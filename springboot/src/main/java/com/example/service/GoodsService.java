package com.example.service;

import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsService {
        @Resource
        private GoodsMapper goodsMapper;

        /**
         * 新增
         */
        public void add(Goods goods) {
            Account currentUser = TokenUtils.getCurrentUser();
            if (RoleEnum.BUSINESS.name().equals(currentUser.getRole())) {
                goods.setBusinessId(currentUser.getId());
            }
            goodsMapper.insert(goods);
        }

        /**
         * 删除
         */
        public void deleteById(Integer id) {
            goodsMapper.deleteById(id);
        }

        /**
         * 批量删除
         */
        public void deleteBatch(List<Integer> ids) {
            for (Integer id : ids) {
                goodsMapper.deleteById(id);
            }
        }

        /**
         * 修改
         */
        public void updateById(Goods goods) {
            goodsMapper.updateById(goods);
        }

        /**
         * 根据ID查询
         */
        public Goods selectById(Integer id) {
            return goodsMapper.selectById(id);
        }

        /**
         * 查询所有
         */
        public List<Goods> selectAll(Goods goods) {
            return goodsMapper.selectAll(goods);
        }

        /**
         * 分页查询
         */
        public PageInfo<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
            PageHelper.startPage(pageNum, pageSize);
            List<Goods> list = goodsMapper.selectAll(goods);
            return PageInfo.of(list);
        }

    }

