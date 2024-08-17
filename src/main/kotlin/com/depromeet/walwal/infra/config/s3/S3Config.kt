package com.depromeet.walwal.infra.config.s3

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.depromeet.walwal.infra.properties.S3Properties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config(
	private val s3Properties: S3Properties,
) {
	@Bean
	fun amazonS3(): AmazonS3 {
		val credentials: AWSCredentials =
			BasicAWSCredentials(s3Properties.accessKey, s3Properties.secretKey)
		val endpointConfiguration =
			AwsClientBuilder.EndpointConfiguration(
				s3Properties.endpoint,
				s3Properties.region,
			)

		return AmazonS3ClientBuilder.standard()
			.withEndpointConfiguration(endpointConfiguration)
			.withCredentials(AWSStaticCredentialsProvider(credentials))
			.build()
	}
}
