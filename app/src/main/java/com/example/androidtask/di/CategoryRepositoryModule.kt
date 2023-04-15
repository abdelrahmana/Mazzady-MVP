package com.example.androidtask.di

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.bumptech.glide.util.Util
import com.example.androidtask.data.source.remote.EndPoints
import com.example.androidtask.data.source.remote.repository.CategoryOptionsRepository
import com.example.androidtask.data.source.remote.repository.CategoryOptionsRepositoryImplementer
import com.example.androidtask.databinding.LoaderLayoutBinding
import com.example.androidtask.util.CommonUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Qualifier
@Module
@InstallIn(ViewModelComponent::class, FragmentComponent::class,ActivityComponent::class,ServiceComponent::class)
class CategoryRepositoryModule {
    @Provides
    fun getCategoryRepository(endPoints: EndPoints,@ApplicationContext context: Context?) : CategoryOptionsRepository {
      return CategoryOptionsRepositoryImplementer(endPoints,context!!)
    }

}