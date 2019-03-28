package jmh.benchmarks.mapper;

import jmh.benchmarks.bean.FormUser;
import jmh.benchmarks.bean.ToUser;
import org.springframework.cglib.beans.BeanCopier;

/**
 * 用户 拷贝
 *
 * @author L.cm
 */
public interface CglibMapper {

	BeanCopier MAPPER = BeanCopier.create(FormUser.class, ToUser.class, false);
}
