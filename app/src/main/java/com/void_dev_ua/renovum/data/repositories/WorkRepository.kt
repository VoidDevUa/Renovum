package com.void_dev_ua.renovum.data.repositories

import com.void_dev_ua.renovum.data.local.daos.AppliedWorkDao
import com.void_dev_ua.renovum.model.AppliedWork
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkRepository @Inject constructor(private val appliedWorkDao: AppliedWorkDao) {
	val allWorks: Flow<List<AppliedWork>> = appliedWorkDao.getAllWorks()

	suspend fun insert(work: AppliedWork) = appliedWorkDao.insert(work)
	suspend fun delete(work: AppliedWork) = appliedWorkDao.delete(work)
	suspend fun update(work: AppliedWork) = appliedWorkDao.update(work)
	suspend fun deleteWorksByRoomId(roomId: String) = appliedWorkDao.deleteByRoomId(roomId)
}