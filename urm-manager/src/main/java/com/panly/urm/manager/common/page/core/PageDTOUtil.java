package com.panly.urm.manager.common.page.core;

import java.util.List;

import com.panly.umr.common.BeanCopyUtil;
import com.panly.umr.common.ReflectUtil;
import com.panly.urm.manager.common.web.JsonResult;

/**
 * <p>
 * PageHelper 插件的处理
 * </p>
 *
 * @project core
 * @class PageDTOUtil
 * @author a@panly.me
 * @date 2017年8月8日下午3:26:28
 */
public class PageDTOUtil {

	public static void startPage(Object params) {
		int pageNum = (int) ReflectUtil.forceGetProperty(params, "pageNum");
		int pageSize = (int) ReflectUtil.forceGetProperty(params, "pageSize");
		PageDTO<?> pageDTO = new PageDTO<>(pageNum, pageSize);
		PageThreadLocal.init(pageDTO);
	}

	public static void startDataTablePage(DataTablePageBase params) {
		int start = (int) ReflectUtil.forceGetProperty(params, "start");
		int length = (int) ReflectUtil.forceGetProperty(params, "length");
		int draw = (int) ReflectUtil.forceGetProperty(params, "draw");
		// 获取 pageNum
		int pageNum = getPageNum(start, length);
		PageDTO<?> pageDTO = new PageDTO<>(pageNum, length);
		pageDTO.setDraw(draw);
		PageThreadLocal.init(pageDTO);
	}

	private static int getPageNum(int start, int length) {
		if (start == 0) {
			return 1;
		} else {
			return (start / length) + 1;
		}
	}

	public static void endPage() {
		PageThreadLocal.remove();
	}

	public static PageDTO<?> getCurrent() {
		return PageThreadLocal.get();
	}

	/**
	 * 在 page startPage 和 endPage 方法之间的查询语句返回的List对象进行转换，
	 * 
	 * @param list
	 * @return
	 */
	public static <T> PageDTO<T> transform(List<?> list, Class<T> clazz) {
		if (PageDTOUtil.getCurrent() == null) {
			throw new RuntimeException("请对在 startPage和 endPage之间的dao查询结果进行转换");
		}
		PageDTO<T> pageDTO = new PageDTO<>(PageDTOUtil.getCurrent().getPageNum(),
				PageDTOUtil.getCurrent().getPageSize());
		pageDTO.setTotal(PageDTOUtil.getCurrent().getTotal());
		List<T> resultData = BeanCopyUtil.copyList(list, clazz);
		pageDTO.setResultData(resultData);
		return pageDTO;
	}

	/**
	 * 在 page startPage 和 endPage 方法之间的查询语句返回的List对象进行转换，
	 * 
	 * @param list
	 * @return
	 **/
	public static <T> PageDTO<T> transform(List<T> list) {
		if (PageDTOUtil.getCurrent() == null) {
			throw new RuntimeException("请对在 startPage和 endPage之间的dao查询结果进行转换");
		}
		PageDTO<T> pageDTO = new PageDTO<>(PageDTOUtil.getCurrent().getPageNum(),
				PageDTOUtil.getCurrent().getPageSize());
		pageDTO.setTotal(PageDTOUtil.getCurrent().getTotal());
		pageDTO.setResultData(list);
		return pageDTO;
	}

	public static JsonResult changePageToDataTableResult(PageDTO<?> page) {
		JsonResult r = new JsonResult();
		r.setData(page.getResultData());
		r.put("recordsTotal", page.getTotal());
		r.put("recordsFiltered", page.getTotal());
		r.put("draw", page.getDraw());
		return r;
	}

}
