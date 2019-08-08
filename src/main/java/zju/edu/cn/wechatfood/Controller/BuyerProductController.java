package zju.edu.cn.wechatfood.Controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zju.edu.cn.wechatfood.Service.Category;
import zju.edu.cn.wechatfood.Service.ProductService;
import zju.edu.cn.wechatfood.VO.ProductInfoVO;
import zju.edu.cn.wechatfood.VO.ProductVO;
import zju.edu.cn.wechatfood.VO.ResultVO;
import zju.edu.cn.wechatfood.dataobjcet.ProductCategory;
import zju.edu.cn.wechatfood.dataobjcet.ProductInfo;
import zju.edu.cn.wechatfood.utils.ResultVOUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
//  @Autowired
//  private ProductService productService;
//  @Autowired
//  private Category categoryService;
//
//  @GetMapping("/list")
//    public ResultVO list(){
////    1.查询所有的上架商品
//    List<Productinfo> productinfoList = productService.findUpAll();
////      2.查询类目(一次性查询）
//      List<Integer> categoryTypeList = productinfoList.stream().map(e->e.getCategoryType()).collect(Collectors.toList());
//  for (Productinfo productinfo : productinfoList) {
//           categoryTypeList.add(productinfo.getCategoryType());
//    }
//
//  List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
////      3.数据拼装
//      List<ProductVO> productVOList = new ArrayList<>();
//    for(ProductCategory productCategory : productCategoryList){
//        ProductVO productVO = new ProductVO();
//        productVO.setCategoryType(productVO.getCategoryType());
//        productVO.setCategoryName(productVO.getCategoryName());
//
//        List<ProductInfoVO> productInfoVOList = new ArrayList<>();
//        for(Productinfo productinfo : productinfoList){
//            if(productinfo.getCategoryType().equals(productCategory.getCategoryType())){
//                ProductInfoVO productInfoVO = new ProductInfoVO();
//                BeanUtils.copyProperties(productinfo, productInfoVO);
//                productInfoVOList.add(productInfoVO);
//
//            }
//        }
//        productVO.setProductInfoVOList(productInfoVOList);
//        productVOList.add(productVO);
//    }
//      ResultVO resultVO = new ResultVO();
//      resultVO.setData(productVOList);
//      resultVO.setCode(0);
//      resultVO.setMessage("成功");
//    return resultVO;
//  }

    @Autowired
    private ProductService productService;

    @Autowired
    private Category categoryService;

    @GetMapping("/list")
    //condition代表只有当条件成立时才会缓存,如#sellerId.length() > 3; 可以使用参数作为KEY，如list带参数（String sellerId）,key = "#sellerId"
//    @Cacheable(cacheNames = "product", key = "123")
    public ResultVO list() {
        //1. 查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2. 查询类目(一次性查询)
//        List<Integer> categoryTypeList = new ArrayList<>();
        //传统方法
//        for (ProductInfo productInfo : productInfoList) {
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //精简方法(java8, lambda)
        //从商品信息集合中取出类目编号
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        //按类目编号取出所有商品类目
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3. 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory: productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            //遍历所有商品 讲与类目名对应的商品存储进Volist
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            //
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
