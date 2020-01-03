package com.ymw.admin.model.sys;

import com.baomidou.mybatisplus.annotations.TableField;

import java.util.List;

public class SysDept extends BaseModel {

    private String name;
    
    private Long parentId;

    private Integer orderNum;

    private Byte delFlag;

    @TableField(exist = false)
    private List<SysDept> children;
    
    // 非数据库字段
	@TableField(exist = false)
    private String parentName;

    // 非数据库字段
	@TableField(exist = false)
    private Integer level;

    private String description;

    private String isUse;

    private Long userId;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Byte getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Byte delFlag) {
		this.delFlag = delFlag;
	}

	public List<SysDept> getChildren() {
		return children;
	}

	public void setChildren(List<SysDept> children) {
		this.children = children;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "SysDept{" +
				"name='" + name + '\'' +
				", parentId=" + parentId +
				", orderNum=" + orderNum +
				", delFlag=" + delFlag +
				", children=" + children +
				", parentName='" + parentName + '\'' +
				", level=" + level +
				", description='" + description + '\'' +
				", isUse='" + isUse + '\'' +
				", userId=" + userId +
				'}';
	}
}