package com.example.projectdemo.ads

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

/**
 * Banner Ad View Component for Jetpack Compose
 * Based on Google AdMob Banner Ads documentation
 */
@Composable
fun BannerAdView(
    modifier: Modifier = Modifier,
    adUnitId: String = "ca-app-pub-3940256099942544/9214589741" // Test ad unit ID
) {
    val context = LocalContext.current
    
    val adView = remember {
        AdView(context).apply {
            this.adUnitId = adUnitId
            // Request an anchored adaptive banner with a width of 360
            setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, 360))
            
            // Set up ad listener
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    // Ad loaded successfully
                    Log.d("BannerAdView", "Banner ad loaded successfully")
                }
                
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Ad failed to load
                    Log.e("BannerAdView", "Banner ad failed to load: ${adError.message}")
                }
                
                override fun onAdOpened() {
                    // Ad opened
                    Log.d("BannerAdView", "Banner ad opened")
                }
                
                override fun onAdClosed() {
                    // Ad closed
                    Log.d("BannerAdView", "Banner ad closed")
                }
                
                override fun onAdClicked() {
                    // Ad clicked
                    Log.d("BannerAdView", "Banner ad clicked")
                }
                
                override fun onAdImpression() {
                    // Ad impression recorded
                    Log.d("BannerAdView", "Banner ad impression recorded")
                }
            }
        }
    }
    
    // Load the ad
    DisposableEffect(Unit) {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        
        onDispose {
            adView.destroy()
        }
    }
    
    // Display the ad view
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        AndroidView(
            factory = { adView },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
