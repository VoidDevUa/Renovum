package com.ulrezaj.renovum_1.data.repositories

import com.ulrezaj.renovum_1.data.local.daos.AppliedWorkDao
import com.ulrezaj.renovum_1.data.model.AppliedWork
import kotlinx.coroutines.flow.Flow

class WorkRepository (private val appliedWorkDao: AppliedWorkDao) {
	val allWorks: Flow<List<AppliedWork>> = appliedWorkDao.getAllWorks()

	suspend fun insert(work: AppliedWork) = appliedWorkDao.insert(work)
	suspend fun delete(work: AppliedWork) = appliedWorkDao.delete(work)
	suspend fun update(work: AppliedWork) = appliedWorkDao.update(work)
	suspend fun deleteWorksByRoomId(roomId: String) = appliedWorkDao.deleteByRoomId(roomId)
}