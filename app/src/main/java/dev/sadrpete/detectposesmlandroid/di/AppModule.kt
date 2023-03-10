package dev.sadrpete.detectposesmlandroid.di

import android.content.Context
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sadrpete.detectposesmlandroid.model.MLPoseDetector
import dev.sadrpete.detectposesmlandroid.utils.VisionImageProcessor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun provideMLBuildOptions(): PoseDetectorOptions{
        return PoseDetectorOptions.Builder()
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()
    }

    @Singleton
    @Provides
    fun provideImageProcessor(context: Context, options: PoseDetectorOptions): VisionImageProcessor {
        return MLPoseDetector(context, options, false)
    }
}