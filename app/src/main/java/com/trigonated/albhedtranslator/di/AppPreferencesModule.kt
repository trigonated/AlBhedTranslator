package com.trigonated.albhedtranslator.di

import android.content.Context
import com.trigonated.albhedtranslator.misc.AndroidAppPreferencesImpl
import com.trigonated.albhedtranslator.model.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppPreferencesModule {

    @Singleton
    @Provides
    fun provideAppPreferences(@ApplicationContext context: Context): AppPreferences {
        return AndroidAppPreferencesImpl(context)
    }
}