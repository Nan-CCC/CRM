package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.VO.MarketVO;
import com.example.enterprisecrm.entity.Contract;
import com.example.enterprisecrm.entity.Implement;
import com.example.enterprisecrm.entity.Marketing;
import com.example.enterprisecrm.entity.Plan;
import com.example.enterprisecrm.mapper.ImplementMapper;
import com.example.enterprisecrm.mapper.MarketingMapper;
import com.example.enterprisecrm.mapper.PlanMapper;
import com.example.enterprisecrm.service.MarketingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MarketingServiceImpl extends ServiceImpl<MarketingMapper, Marketing> implements MarketingService {
    @Resource
    private MarketingMapper mapper;
    @Resource
    private PlanMapper planMapper;

    @Resource
    private ImplementMapper implementMapper;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Override
    public String upload(MultipartFile file, HttpServletRequest req) {
        UUID uuid = UUID.randomUUID();
        String string = uuid.toString();
        //获取文件原始名
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成新文件名
        String newFileName=string+extension;
        File dateDir = new File(uploadFolder);
        //如果不存在就创建
        if(!dateDir.exists()){
            dateDir.mkdirs();
        }
        try{
            file.transferTo(new File(dateDir,newFileName));
        }catch (IOException e){
            e.printStackTrace();
        }
        String invented_address =
                req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort() + "/api/file/" + newFileName;
        return invented_address;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String insertMartet(MarketVO marketVO) {
        try{
            Marketing marketing = new Marketing();
            marketing.setId(marketVO.getId());
            marketing.setName(marketVO.getName());
            marketing.setStatus(marketVO.getStatus());

            marketing.setSubmit( marketVO.getSubmit());
            marketing.setStart(marketVO.getStart());
            marketing.setEnd(marketVO.getEnd());

            marketing.setInfo(marketVO.getInfo());
            marketing.setCost(marketVO.getCost());
            int insert = mapper.insert(marketing);

            QueryWrapper<Marketing> marketingQueryWrapper = new QueryWrapper<>();
            marketingQueryWrapper.eq("mk_submit",marketVO.getSubmit())
                    .eq("mk_name",marketVO.getName())
                    .eq("mk_info",marketVO.getInfo());
            Marketing marketing1 = mapper.selectOne(marketingQueryWrapper);
            String id = marketing1.getId();


            List<String> uidList = marketVO.getUidList();
            for(int i=0;i<uidList.size();i++ ){
                Plan plan = new Plan();
                plan.setId(0);
                plan.setUid(uidList.get(i));
                plan.setMid(id);
                int insert1 = planMapper.insert(plan);
            }

            List<String> pidList = marketVO.getPidList();
            for (int i=0;i<pidList.size();i++){
                Implement implement = new Implement();
                implement.setId(0);
                implement.setMid(id);
                implement.setPid(pidList.get(i));
                int insert1 = implementMapper.insert(implement);
            }
            return id;
        }catch (Exception e){
            throw new RuntimeException("营销活动插入失败");
        }

    }

    @Override
    public int updateMartet( Marketing marketing) {
        int i = mapper.updateById(marketing);
        return i;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int deleteMartet(String id) {
       try {
           int i = mapper.deleteById(id);
           QueryWrapper<Implement> implementQueryWrapper = new QueryWrapper<>();
           implementQueryWrapper.eq("market_id",id);
           int delete = implementMapper.delete(implementQueryWrapper);
           QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
           planQueryWrapper.eq("market_id",id);
           int delete1 = planMapper.delete(planQueryWrapper);
           return i;
       }catch (Exception e){
           throw new RuntimeException("删除失败");
       }
    }

    @Override
    public Page<Marketing> selectAll(int a,int b) {
        QueryWrapper<Marketing> marketingQueryWrapper = new QueryWrapper<>();
        marketingQueryWrapper.orderByDesc("mk_id");
        Page<Marketing> page = new Page<>();
        page.setCurrent(a);
        page.setSize(b);
        mapper.selectPage(page, marketingQueryWrapper);
        return page;
    }

    @Override
    public String download(String cid) {
        QueryWrapper<Marketing> wrapper = new QueryWrapper<>();
        wrapper.eq("mk_id",cid);
        Marketing marketing = mapper.selectOne(wrapper);
        String[] split = marketing.getInfo().split("/");
        String fileName=split[split.length-1];

        String filePath=uploadFolder+fileName;
        return filePath;
    }
    @Override
    public Marketing selectMarket( String id) {
        Marketing marketing = mapper.selectById(id);
        return marketing;
    }
}
