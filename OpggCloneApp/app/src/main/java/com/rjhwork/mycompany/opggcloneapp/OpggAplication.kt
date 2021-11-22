package com.rjhwork.mycompany.opggcloneapp

import android.app.Application
import com.rjhwork.mycompany.opggcloneapp.di.appModule
import com.rjhwork.mycompany.opggcloneapp.di.dataModule
import com.rjhwork.mycompany.opggcloneapp.di.domainModule
import com.rjhwork.mycompany.opggcloneapp.di.presenterModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class OpggAplication: Application()  {

    override fun onCreate() {
        super.onCreate()
        // Dependency inversion 을 위한 Koin 시작.
        startKoin {
            // 빌드상 에러 발생시 로그 출력
            androidLogger(
                if(BuildConfig.DEBUG) {
                    Level.DEBUG
                } else {
                    Level.NONE
                }
            )
            androidContext(this@OpggAplication)
            // modules 에서 필요한 레이어의 객체 생성 제어를 넘겨주게된다.
            modules(appModule
                    + dataModule
                    + domainModule
                    + presenterModule
            )
        }
    }
}