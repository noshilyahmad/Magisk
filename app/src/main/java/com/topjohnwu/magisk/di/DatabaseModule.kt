package com.topjohnwu.magisk.di

import android.content.Context
import androidx.room.Room
import com.topjohnwu.magisk.data.database.*
import com.topjohnwu.magisk.tasks.RepoUpdater
import org.koin.dsl.module


val databaseModule = module {
    single { PolicyDao(get()) }
    single { SettingsDao() }
    single { StringDao() }
    single { createRepoDatabase(get()) }
    single { get<RepoDatabase>().repoDao() }
    single { get<RepoDatabase>().repoByNameDao() }
    single { get<RepoDatabase>().repoByUpdatedDao() }
    single { createSuLogDatabase(get(Protected)).suLogDao() }
    single { RepoUpdater(get(), get()) }
}

fun createRepoDatabase(context: Context) =
    Room.databaseBuilder(context, RepoDatabase::class.java, "repo.db")
        .fallbackToDestructiveMigration()
        .build()

fun createSuLogDatabase(context: Context) =
    Room.databaseBuilder(context, SuLogDatabase::class.java, "sulogs.db")
        .fallbackToDestructiveMigration()
        .build()
