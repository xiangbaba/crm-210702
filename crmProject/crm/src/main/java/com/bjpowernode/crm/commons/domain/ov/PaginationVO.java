package com.bjpowernode.crm.commons.domain.ov;

import java.util.List;

/**
 * @author lzx
 * @create 2021/6/8 17:18
 */
public class PaginationVO<T> {
	private Integer totalRows;
	private List<T> activityList;

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public List<T> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<T> activityList) {
		this.activityList = activityList;
	}

	@Override
	public String toString() {
		return "paginationVO{" +
				"totalRows=" + totalRows +
				", activityList=" + activityList +
				'}';
	}
}
