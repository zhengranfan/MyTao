package com.mytao.service;

import java.util.List;

import com.mytao.common.pojo.EUTreeNode;
import com.mytao.common.pojo.MytaoResult;

public interface ContentCategoryService {

		List<EUTreeNode> getCategoryList(long parentId);
		MytaoResult insertConentCategory(long parentId,String name);
		MytaoResult deleteContentCategory(long id);
		MytaoResult updateContentCategory(long id,String name);
}
