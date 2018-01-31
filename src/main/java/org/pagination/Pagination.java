package org.pagination;

import java.util.List;

public interface Pagination<T> {
	public Integer getTotalElements();

	public List<T> getPageList();
}
