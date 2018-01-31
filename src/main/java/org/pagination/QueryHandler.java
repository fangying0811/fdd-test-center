package org.pagination;

import java.util.List;

public interface QueryHandler<T> {
	public Integer getCount();

	public List<T> getData(Integer pageIndex, Integer pageSize);
}
