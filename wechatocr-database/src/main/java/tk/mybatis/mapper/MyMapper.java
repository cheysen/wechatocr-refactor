package mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author Cheysen
 * @Description 通用Mapper,该接口不能被扫描到否则会出错
 * @Date 2019/8/27 20:19
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
