package com.cy.pj.common.bo;//业务层向外输出的一个business object 业务对象

import java.io.Serializable;
import java.util.List;

import com.cy.pj.sys.entity.SysLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**定义POJO的规范  每一层都需要封装，表示这个层对应的业务。
 * POJO(VO/BO/DTO/DO)
 * 1)所有的POJO对象属性定义都用包装类型
 * 2)所有的POJO对象属性默认值无序指定
 * 3)所有的POJO对象都需要提供set/get方法。
 * 4)所有的POJO都需要提供无参构造函数
 * 5)所有的POJO对象的构造方法里面都不要写业务逻辑
 * 6)所有的POJO类必须重写toString方法
 * //业务层向外输出的一个business object 业务对象
 * @author Administrator
 *
 * @param <T> 需要封装的值可以是多个类型，提高代码通用
 * 泛型:类上定义的泛型用于约束类中的属性，方法参数，方法返回值类型。
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageObject<T> implements Serializable{
	  /**
	 * 在写完所有属性之后在添加id
	 */
	private static final long serialVersionUID = -2046994691382539124L;
	/**当前页记录*/
    private List<T> records;
    /**总行数(通过查询获得)*/
    private Integer rowCount;
    /**总页数(通过计算获得)*/
    private Integer pageCount;
    /**页面大小，每页要多少条记录*/
    private Integer pageSize;
    /**当前页的页码值*/
    private Integer pageCurrent;
    /** */
	public PageObject(List<T> records, Integer rowCount, Integer pageSize, Integer pageCurrent) {
		super();
		this.records = records;
		this.rowCount = rowCount;
		this.pageSize = pageSize;
		this.pageCurrent = pageCurrent;
		this.pageCount = rowCount/pageSize;
		if(rowCount%pageSize!=0) {//"/" 是取整 如果除不尽 就加1
			this.pageCount++;
		}//this.pageCount=((rowCount-1)/pageSize)+1 计算分页，(10-1 / 3=3 3+1=4 页)和上面的效果一样 
	}
	
}
