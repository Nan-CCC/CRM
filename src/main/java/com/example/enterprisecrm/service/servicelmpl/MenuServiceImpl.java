package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.Menu;
import com.example.enterprisecrm.mapper.MenuMapper;
import com.example.enterprisecrm.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    private MenuMapper mapper;
    @Override
    public List<Menu> selectAllByRole(String role) {
        //一层
        List<Menu> menus= null;
        //二层
        List<Menu> children = null;
        //三层
        List<Menu> children2= null;
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        //(A or B) and C
        wrapper.isNull("pid")
                .and(q->q.eq("role",role).or().eq("role","public"));
        menus = mapper.selectList(wrapper);
        for(Menu i : menus){
            QueryWrapper<Menu> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("pid",i.getId())
                    .and(q->q.eq("role",role).or().eq("role","public"));
             children = mapper.selectList(wrapper2);
            System.out.println("---------------");
            System.out.println(children);
            for(Menu c : children){
                QueryWrapper<Menu> wrapper3 = new QueryWrapper<>();
                wrapper3.eq("pid",c.getId())
                        .and(q->q.eq("role",role).or().eq("role","public"));
                children2 = mapper.selectList(wrapper3);

                c.setChildren(children2);
            }
            i.setChildren(children);
        }


        return menus;
    }
}
