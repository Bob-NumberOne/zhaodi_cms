package com.zhaodi.bean;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class Page<T> {
	private static final int PAGE_NUM = 1;
    private static final int PAGE_SIZE = 10;

    // 总页数
    private int pageSum;
    // 当前页,默认为1
    private int pageNum = PAGE_NUM;
    // 每页显示条数
    private int pageSize = PAGE_SIZE;
    // 总条数
    private int counts;
    // 分页查询的开始位置
    private int start;
    // 具体分页的javaBean对象
    private T bean;
    // 分页查询出来的结果
    private List<T> beans;
    // 分页需要的特殊查询条件
    private String criteria;
    //分页数据是否对应本人(合同是否终止1.正常2.终止)
    private String corresponding;
    //分页开始时间
    private String startTime;
    //分页结束时间
    private String endTime;
    //分页查询条件人id
    private Long userId;
    //状态
    private Integer status;
    //信息
    private String message;

    private Integer totalSize;


    public Integer getTotalSize() {
        Integer total_Size=pageSize*this.getPageNum();
        return total_Size;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCorresponding() {
		return corresponding;
	}

	public void setCorresponding(String corresponding) {
		this.corresponding = corresponding;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public List<T> getBeans() {
        return beans;
    }

    public void setBeans(List<T> beans) {
        this.beans = beans;
    }

    public int getPageSum() {
        this.pageSum = (int)Math.ceil(1.0 * getCounts() / getPageSize());
        return pageSum;
    }
    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(String pageNumStr) {
        if(StringUtils.isNotEmpty(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        } else {
            pageNum = PAGE_NUM;
        }

    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(String pageSizeStr) {
        if(StringUtils.isNotEmpty(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = PAGE_SIZE;
        }

    }


    public int getCounts() {
        return counts;
    }
    public void setCounts(int counts) {
        this.counts = counts;
    }
    public int getStart() {
        start = (getPageNum() - 1) * getPageSize();
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public T getBean() {
        return bean;
    }
    public void setBean(T bean) {
        this.bean = bean;
    }
}
