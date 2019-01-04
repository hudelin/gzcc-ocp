//// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://kpdus.tripod.com/jad.html
//// Decompiler options: packimports(3) fieldsfirst ansi space
//// Source File Name:   TableResultResponse.java
//
//package com.hdl.gzccocpweb.support;
//
//import java.util.ArrayList;
//import java.util.List;
//
//// Referenced classes of package net.shadowedu.metis.common.base.response:
////			BaseResponse
//
//public class TableResultResponse extends BaseResponse
//{
//	public class TableData
//	{
//
//		Integer total;
//		List rows;
//		final TableResultResponse this$0;
//
//		public Integer getTotal()
//		{
//			return total;
//		}
//
//		public void setTotal(Integer total)
//		{
//			this.total = total;
//		}
//
//		public List getRows()
//		{
//			return rows;
//		}
//
//		public void setRows(List rows)
//		{
//			this.rows = rows;
//		}
//
//		public TableData(Integer total, List rows)
//		{
//			this.this$0 = TableResultResponse.this;
//			super();
//			this.total = Integer.valueOf(0);
//			this.rows = new ArrayList();
//			this.total = total;
//			this.rows = rows;
//		}
//
//		public TableData()
//		{
//			this.this$0 = TableResultResponse.this;
//			super();
//			total = Integer.valueOf(0);
//			rows = new ArrayList();
//		}
//	}
//
//
//	private static final long serialVersionUID = 1L;
//	TableData data;
//
//	public TableResultResponse(Integer total, List rows)
//	{
//		data = new TableData(total, rows);
//	}
//
//	public TableResultResponse()
//	{
//		data = new TableData();
//	}
//
//	TableResultResponse total(Integer total)
//	{
//		data.setTotal(total);
//		return this;
//	}
//
//	TableResultResponse total(List rows)
//	{
//		data.setRows(rows);
//		return this;
//	}
//
//	public TableData getData()
//	{
//		return data;
//	}
//
//	public void setData(TableData data)
//	{
//		this.data = data;
//	}
//}
