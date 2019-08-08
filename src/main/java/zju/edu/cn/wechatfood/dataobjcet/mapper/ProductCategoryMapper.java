package zju.edu.cn.wechatfood.dataobjcet.mapper;


import org.apache.ibatis.annotations.*;
import zju.edu.cn.wechatfood.dataobjcet.ProductCategory;

import java.util.List;
import java.util.Map;

public interface ProductCategoryMapper {
     @Insert("insert into product_category(category_name, category_type) values (#{category_name, jdbcType=VARCHAR}, #{category_type, jdbcType=INTEGER})")
     int insertByMap(Map<String, Object> map);

     @Insert("insert into product_category(category_name, category_type) values (#{category_name, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
     int insertByObject(ProductCategory productCategory);

     @Select("select * product_category from where category_type = #{categoryType} ")

     @Results({
             @Result(column = "category_id", property = "category_id"),
             @Result(column = "category_type", property = "categoryType"),
             @Result(column = "category_type", property = "categoryType"),
     })

     List<ProductCategory> findByCategoryType(Integer categoryType);

     @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
     int updateByCategoryType(@Param("categoryName")String categoryName,
                              @Param("categoryType") Integer categoryType);

     @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
     int updateByObject(ProductCategory productCategory);
}

