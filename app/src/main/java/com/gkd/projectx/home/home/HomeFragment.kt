package com.gkd.projectx.home.home

import com.gkd.projectx.common.BaseFragment
import com.gkd.projectx.databinding.FragmentHomeBinding
import com.gkd.projectx.home.home.contract.HomeAction
import com.gkd.projectx.home.home.contract.HomeIntent
import com.gkd.projectx.home.home.contract.HomeState

class HomeFragment :
    BaseFragment<HomeIntent, HomeAction, HomeState, HomeViewModel, FragmentHomeBinding>(
        HomeViewModel::class.java,
        { inflater, container ->
            FragmentHomeBinding.inflate(inflater, container, false)
        }) {

    override fun initUI() {

    }

    override fun initDATA() {

    }

    override fun initEVENT() {

    }

    override fun render(state: HomeState) {

    }

}