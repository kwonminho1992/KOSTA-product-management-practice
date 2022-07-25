package com.my.repository;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.my.dto.Product;
import com.my.exception.AddException;
import com.my.exception.FindException;

@Repository(value = "productRepository")
public class ProductOracleRepository implements ProductRepository {
  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  public void insert(Product product) throws AddException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      session.insert("com.my.mapper.ProductMapper.insert", product);
    } catch (Exception e) {
      throw new AddException(e.getMessage());
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  public void update(Product product) throws AddException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      session.update("com.my.mapper.ProductMapper.update", product);
    } catch (Exception e) {
      throw new AddException(e.getMessage());
    } finally {
      if (session != null) {
        session.close();
      }
    }

  }

  public List<Product> selectAll() throws FindException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      List<Product> products = session.selectList("com.my.mapper.ProductMapper.selectAll");
      if (products.size() == 0) {
        throw new FindException("상품이 없습니다.");
      }
      return products;
    } catch (Exception e) {
      throw new FindException(e.getMessage());
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  public Product selectByProductNo(String productNo) throws FindException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      Product product =
          session.selectOne("com.my.mapper.ProductMapper.selectByProductNo", productNo);
      if (product == null) {
        throw new FindException("없는 상품번호입니다.");
      }
      return product;
    } catch (Exception e) {
      throw new FindException(e.getMessage());
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  public List<Product> selectByProductNoOrName(String keyword) throws FindException {
    SqlSession session = null;
    try {
      session = sqlSessionFactory.openSession(); // Connection과 같은 역할을함
      List<Product> products =
          session.selectList("com.my.mapper.ProductMapper.selectByProductNoOrName", keyword);
      return products;
    } catch (Exception e) {
      throw new FindException(e.getMessage());
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }
}
