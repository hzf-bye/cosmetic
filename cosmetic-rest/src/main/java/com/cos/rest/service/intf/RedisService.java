package com.cos.rest.service.intf;


import com.cos.common.entity.BizResult;

public interface RedisService {

	BizResult syncContent(long contentCid);
}
