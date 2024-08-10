package com.depromeet.walwal.global.config.fcm

import com.depromeet.walwal.infra.properties.FcmProperties
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets

@Configuration
class FcmConfig(
	private val fcmProperties: FcmProperties,
) {
	@Bean
	@Throws(IOException::class)
	fun firebaseMessaging(): FirebaseMessaging {
		val firebaseCredential: String = fcmProperties.credential

		check(firebaseCredential.isNotEmpty()) { "FCM_CREDENTIAL environment variable is not set." }

		val credentials: ByteArrayInputStream =
			ByteArrayInputStream(firebaseCredential.toByteArray(StandardCharsets.UTF_8))
		val options: FirebaseOptions =
			FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(credentials))
				.build()

		val firebaseApp: FirebaseApp = FirebaseApp.initializeApp(options)
		return FirebaseMessaging.getInstance(firebaseApp)
	}
}
