package com.mytao.service;

import com.mytao.common.pojo.EUDataGridResult;
import com.mytao.common.pojo.MytaoResult;
import com.mytao.pojo.TbItemParam;

public interface ItemParamService {

		MytaoResult getItemParamByCid(long cid);
		MytaoResult insertItemParam(TbItemParam itemParam);
		EUDataGridResult getItemParamList(int page, int rows);
}
