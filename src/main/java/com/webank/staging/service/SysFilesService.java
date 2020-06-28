package com.webank.staging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.webank.staging.common.utils.DataResult;
import com.webank.staging.entity.SysFilesEntity;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件上传 服务类
 *
 *
 * 
 * 
 */
public interface SysFilesService extends IService<SysFilesEntity> {

    DataResult saveFile(MultipartFile file);

    void removeByIdsAndFiles(List<String> ids);
}

