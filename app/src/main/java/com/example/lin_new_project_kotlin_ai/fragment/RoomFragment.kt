package com.example.lin_new_project_kotlin_ai.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.lin_new_project_kotlin_ai.MyApplication.mDatabaseManager
import com.example.lin_new_project_kotlin_ai.R
import com.example.lin_new_project_kotlin_ai.adapter.UserAdapter
import com.example.lin_new_project_kotlin_ai.databinding.FragmentRoomBinding
import com.example.lin_new_project_kotlin_ai.`fun`.CommonUtils
import com.example.lin_new_project_kotlin_ai.`fun`.RecyclerViewMethed
import com.example.lin_new_project_kotlin_ai.room.DatabaseCallback
import com.example.lin_new_project_kotlin_ai.room.data.FavoritesData
import com.example.lin_new_project_kotlin_ai.room.data.HistoryData
import com.example.lin_new_project_kotlin_ai.room.data.UserData
import com.example.lin_new_project_kotlin_ai.room.entity.FavoritesEntity
import com.example.lin_new_project_kotlin_ai.room.entity.HistoryEntity
import com.example.lin_new_project_kotlin_ai.room.entity.UserProfileEntity
import com.example.lin_new_project_kotlin_ai.viewBinding.BaseBindingFragment
import com.google.gson.Gson

class RoomFragment : BaseBindingFragment<FragmentRoomBinding?>() {
    private var userProfileEntity: UserProfileEntity? = null
    private var userAdapter: UserAdapter? = null

    override fun onCreateViewBinding(layoutInflater: LayoutInflater): FragmentRoomBinding {
        return FragmentRoomBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        // 進入畫面時自動查詢現有資料
        getUserProfileEntityALL()
    }

    private fun initView() {
        val binding = getBinding() ?: return

        RecyclerViewMethed.init_VERTICAL(
            activity, binding.recyclerView,
            true, 0, CommonUtils.getColorByVERSION(R.color.white),
            true, true
        )
        userAdapter = UserAdapter(context, ArrayList())
        userAdapter?.setOnItemClickListener(object : UserAdapter.OnItemClickListener {
            override fun OnItemClick(userProfile: UserProfileEntity?) {
                if (userProfile == null) return
                userProfileEntity = userProfile
                val currentBinding = getBinding() ?: return
                currentBinding.btnDelete.visibility = View.VISIBLE
                currentBinding.tvUser.visibility = View.VISIBLE
                currentBinding.btnInsertFavorites.visibility = View.VISIBLE
                currentBinding.btnInsertHistory.visibility = View.VISIBLE
                currentBinding.tvUser.text = "已選中 ID ${userProfile.uid}:\n" + Gson().toJson(userProfile)
                showToast("已選擇使用者 ID: ${userProfile.uid}")
            }
        })
        binding.recyclerView.adapter = userAdapter

        binding.btnInsert.setOnClickListener { insertUserProfileEntity() }
        binding.btnQuery.setOnClickListener { getUserProfileEntityALL() }
        binding.btnDelete.setOnClickListener { deleteUserProfile() }
        binding.btnClearTable.setOnClickListener { clearTable() }
        binding.btnInsertFavorites.setOnClickListener { insertFavoritesEntity() }
        binding.btnInsertHistory.setOnClickListener { insertHistoryEntity() }
    }

    private fun insertUserProfileEntity() {
        val count = (userAdapter?.itemCount ?: 0) + 1
        val newEntity = UserProfileEntity()
        val userData = UserData()
        userData.userName = "使用者_$count"
        userData.userType = 0
        userData.imageName = "avatar_$count"
        newEntity.userData = userData

        mDatabaseManager().insertUserProfileEntity(
            newEntity,
            object : DatabaseCallback<UserProfileEntity?>() {
                override fun onAdded(rowId: Long) {
                    super.onAdded(rowId)
                    setLog("新增使用者資料, onAdded, rowId: $rowId")
                    showToast("新增使用者成功 (ID: $rowId)")
                    // 新增成功後自動刷新列表
                    getUserProfileEntityALL()
                }

                override fun onError(e: String?) {
                    super.onError(e)
                    setLog("新增使用者失敗: $e")
                    showToast("新增失敗: $e")
                }
            }
        )
    }

    private fun getUserProfileEntityALL() {
        mDatabaseManager().getUserProfilesALL(object : DatabaseCallback<UserProfileEntity?>() {
            override fun onDataLoadedList(lists: MutableList<UserProfileEntity?>?) {
                super.onDataLoadedList(lists)
                val safeList = lists ?: ArrayList()
                setLog("讀取UserProfileEntityALL, size: ${safeList.size}")
                for (item in safeList) {
                    setLog("使用者資料: " + Gson().toJson(item))
                }
                userAdapter?.setArray(safeList)
                if (safeList.isEmpty()) {
                    showToast("目前沒有使用者資料")
                } else {
                    showToast("查詢完成，共 ${safeList.size} 筆資料")
                }
            }

            override fun onError(e: String?) {
                super.onError(e)
                setLog("查詢失敗: $e")
                showToast("查詢失敗: $e")
            }
        })
    }

    private fun deleteUserProfile() {
        val target = userProfileEntity ?: run {
            showToast("請先點選欲刪除的使用者")
            return
        }

        mDatabaseManager().deleteUserProfile(
            target,
            object : DatabaseCallback<UserProfileEntity?>() {
                override fun onDeleted() {
                    super.onDeleted()
                    setLog("onDeleted 成功刪除 UID: ${target.uid}")
                    showToast("已刪除使用者 ID: ${target.uid}")
                    resetDetailSelection()
                    getUserProfileEntityALL()
                }

                override fun onError(e: String?) {
                    super.onError(e)
                    setLog("刪除失敗: $e")
                    showToast("刪除失敗: $e")
                }
            }
        )
    }

    private fun insertHistoryEntity() {
        val target = userProfileEntity ?: run {
            showToast("請先點選使用者")
            return
        }

        val historyEntity = HistoryEntity()
        historyEntity.historyParentUid = target.uid
        val historyData = HistoryData()
        historyData.historyName = "歷史操作_${System.currentTimeMillis() % 1000}"
        historyData.programId = 2026
        historyEntity.historyData = historyData

        mDatabaseManager().insertHistoryEntity(
            historyEntity,
            object : DatabaseCallback<HistoryEntity?>() {
                override fun onAdded(rowId: Long) {
                    super.onAdded(rowId)
                    setLog("新增歷史紀錄成功, rowId: $rowId")
                    showToast("已為使用者 ${target.uid} 新增歷史紀錄")
                }

                override fun onError(e: String?) {
                    super.onError(e)
                    setLog("新增歷史紀錄失敗: $e")
                    showToast("新增歷史失敗: $e")
                }
            }
        )
    }

    private fun insertFavoritesEntity() {
        val target = userProfileEntity ?: run {
            showToast("請先點選使用者")
            return
        }

        val favoritesEntity = FavoritesEntity()
        favoritesEntity.favoriteParentUid = target.uid
        favoritesEntity.favoriteName = "最愛項目"
        favoritesEntity.favoriteType = (1..100).random()
        favoritesEntity.isDefault = true
        val favoritesData = FavoritesData()
        favoritesData.test = "自訂我的最愛"
        favoritesEntity.favoritesData = favoritesData

        mDatabaseManager().insertFavoritesEntity(
            favoritesEntity,
            object : DatabaseCallback<FavoritesEntity?>() {
                override fun onAdded(rowId: Long) {
                    super.onAdded(rowId)
                    setLog("新增我的最愛成功, rowId: $rowId")
                    showToast("已為使用者 ${target.uid} 新增我的最愛")
                }

                override fun onError(e: String?) {
                    super.onError(e)
                    setLog("新增我的最愛失敗: $e")
                    showToast("新增我的最愛失敗: $e")
                }
            }
        )
    }

    private fun clearTable() {
        mDatabaseManager().clearTable(object : DatabaseCallback<Any?>() {
            override fun onDeleted() {
                setLog("清空資料庫資料完成")
                showToast("資料庫已清空")
                resetDetailSelection()
                userAdapter?.setArray(ArrayList())
            }

            override fun onError(e: String?) {
                super.onError(e)
                setLog("清空資料庫失敗: $e")
                showToast("清空失敗: $e")
            }
        })
    }

    private fun resetDetailSelection() {
        userProfileEntity = null
        val binding = getBinding() ?: return
        binding.btnDelete.visibility = View.GONE
        binding.tvUser.visibility = View.GONE
        binding.tvUser.text = ""
        binding.btnInsertFavorites.visibility = View.GONE
        binding.btnInsertHistory.visibility = View.GONE
    }

    override fun onDestroyView() {
        mDatabaseManager().roomClear()
        userAdapter = null
        super.onDestroyView()
    }
}