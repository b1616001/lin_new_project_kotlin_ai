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
    }

    private fun initView() {
        RecyclerViewMethed.init_VERTICAL(
            getActivity(), getBinding()!!.recyclerView,
            true, 0, CommonUtils.getColorByVERSION(R.color.white),
            true, true
        )
        userAdapter = UserAdapter(getContext(), ArrayList())
        userAdapter!!.setOnItemClickListener(object:UserAdapter.OnItemClickListener{
            override fun OnItemClick(userProfile: UserProfileEntity?) {
                userProfileEntity = userProfile
                getBinding()!!.btnDelete.setVisibility(View.VISIBLE)
                getBinding()!!.tvUser.setVisibility(View.VISIBLE)
                //            getBinding().btnInsertFavorites.setVisibility(VISIBLE);
//            getBinding().btnInsertHistory.setVisibility(VISIBLE);
                getBinding()!!.recyclerView.setVisibility(View.INVISIBLE)
                getBinding()!!.tvUser.setText(Gson().toJson(userProfileEntity))
            }
        })
        getBinding()!!.recyclerView.setAdapter(userAdapter)
        getBinding()!!.btnInsert.setOnClickListener { v_: View? -> insertUserProfileEntity() }
        getBinding()!!.btnQuery.setOnClickListener { v: View? -> getUserProfileEntityALL() }
        getBinding()!!.btnDelete.setOnClickListener { v: View? -> deleteUserProfile() }
        getBinding()!!.btnClearTable.setOnClickListener { v: View? -> clearTable() }
        getBinding()!!.btnInsertFavorites.setOnClickListener { v: View? -> insertFavoritesEntity() }
        getBinding()!!.btnInsertHistory.setOnClickListener { v: View? -> insertHistoryEntity() }
    }


    private fun insertUserProfileEntity() {
        val userProfileEntity = UserProfileEntity()
        val userData = UserData()
        userData.userName = "AAA"
        userData.userType = 0
        userData.imageName="aasd"
        userProfileEntity.userData=userData
       mDatabaseManager().insertUserProfileEntity(
            userProfileEntity,
            object : DatabaseCallback<UserProfileEntity?>() {
                override fun onAdded(rowId: Long) {
                    super.onAdded(rowId)
                    //                uid = (int) rowId;//rowId不一定是UID
                    setLog("新增使用者資料,onAdded,rowId:" + rowId)
                }
            })
    }
    private fun getUserProfileEntityALL() {
        mDatabaseManager().getUserProfilesALL(object : DatabaseCallback<UserProfileEntity?>() {
             override fun onDataLoadedList(lists: MutableList<UserProfileEntity?>?) {
                super.onDataLoadedList(lists)
                setLog("讀取UserProfileEntityALL,onDataLoadedList,size:" + lists?.size)
                for (i in lists!!.indices) {
                    val userProfile = lists?.get(i)
                    setLog("使用者資料:" + Gson().toJson(userProfile))
                }
                getBinding()!!.recyclerView.setVisibility(View.VISIBLE)
                userAdapter!!.setArray(lists)
            }

             override fun onError(e: String?) {
                super.onError(e)
                setLog("onError" + e)
            }
        })
    }


    private fun deleteUserProfile() {
        if (userProfileEntity == null) return
       mDatabaseManager()
            .deleteUserProfile(userProfileEntity, object : DatabaseCallback<UserProfileEntity?>() {
                override fun onDeleted() {
                    super.onDeleted()
                    getBinding()!!.btnDelete.setVisibility(View.INVISIBLE)
                    getBinding()!!.tvUser.setVisibility(View.INVISIBLE)

                    //                getBinding().btnInsertFavorites.setVisibility(INVISIBLE);
                    setLog("onDeleted")
                }
            })
    }

    private fun insertHistoryEntity() {
        if (userProfileEntity == null) return
        val historyEntity = HistoryEntity()
        historyEntity.historyParentUid = userProfileEntity!!.uid
        val historyData = HistoryData()
        historyData.historyName = "HHH"
        historyData.programId=2026
        historyEntity.historyData = historyData
        mDatabaseManager()
            .insertHistoryEntity(historyEntity, object : DatabaseCallback<HistoryEntity?>() {
                override fun onAdded(rowId: Long) {
                    super.onAdded(rowId)
                    setLog("新增歷史紀錄,onAdded,rowId:" + rowId)
                }
            })
    }

    private fun insertFavoritesEntity() {
        if (userProfileEntity == null) return
        val favoritesEntity = FavoritesEntity()
        favoritesEntity.favoriteParentUid = userProfileEntity!!.uid
        mDatabaseManager()
            .insertFavoritesEntity(favoritesEntity, object : DatabaseCallback<FavoritesEntity?>() {
                override fun onAdded(rowId: Long) {
                    super.onAdded(rowId)
                    setLog("新增我的最愛,onAdded,rowId:" + rowId)
                }
            })
    }

    private fun clearTable() {
        mDatabaseManager().clearTable(object : DatabaseCallback<Any?>() {
            override fun onDeleted() {
                userProfileEntity = null
                setLog("清空資料庫資料,onDeleted:清空完成")
            }
        })
    }
}