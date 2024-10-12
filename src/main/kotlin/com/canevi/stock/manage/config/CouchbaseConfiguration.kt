package com.canevi.stock.manage.config

import com.couchbase.client.core.error.BucketNotFoundException
import com.couchbase.client.core.error.UnambiguousTimeoutException
import com.couchbase.client.java.Bucket
import com.couchbase.client.java.Cluster
import com.couchbase.client.java.ClusterOptions
import com.couchbase.client.java.env.ClusterEnvironment
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories
import java.time.Duration


@Configuration(proxyBeanMethods = true)
@EnableCouchbaseRepositories
class CouchbaseConfiguration(
    @Value("\${db.host}") val host: String,
    @Value("\${db.user}") val user: String,
    @Value("\${db.pass}") val pass: String,
    @Value("\${db.nameOfBucket}") val nameOfBucket: String
) : AbstractCouchbaseConfiguration() {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun getConnectionString(): String {
        return host
    }
    override fun getUserName(): String {
        return user
    }
    override fun getPassword(): String {
        return pass
    }
    override fun getBucketName(): String {
        return nameOfBucket
    }

    @Bean(destroyMethod = "disconnect")
    override fun couchbaseCluster(couchbaseClusterEnvironment: ClusterEnvironment?): Cluster {
        try {
            log.debug("Connecting to Couchbase cluster at $connectionString")
            val cluster = Cluster.connect(connectionString, ClusterOptions.clusterOptions(userName, password))
            /*
            * ClusterOptions.clusterOptions( userName, password ).environment { env: ClusterEnvironment.Builder ->
                        env.applyProfile(
                            "wan-development"
                        )
                    }
            * */
            cluster.waitUntilReady(Duration.ofSeconds(15))
            return cluster
        } catch (e: UnambiguousTimeoutException) {
            log.error("Connection to Couchbase cluster at $host timed out");
            throw e;
        } catch (e: Exception) {
            log.error(e.javaClass.getName());
            log.error("Could not connect to Couchbase cluster at $host");
            throw e;
        }
    }

    @Bean
    fun getCouchbaseBucket(cluster: Cluster): Bucket? {
        try {
            if (!cluster.buckets().allBuckets.containsKey(bucketName)) {
                throw BucketNotFoundException("Bucket $bucketName does not exist")
            }
            val bucket = cluster.bucket(bucketName)
            bucket.waitUntilReady(Duration.ofSeconds(15))
            return bucket
        } catch (e: UnambiguousTimeoutException) {
            log.error("Connection to bucket $bucketName timed out")
            throw e
        } catch (e: BucketNotFoundException) {
            log.error("Bucket $bucketName does not exist")
            throw e
        } catch (e: java.lang.Exception) {
            log.error(e.javaClass.name)
            log.error("Could not connect to bucket $bucketName")
            throw e
        }
    }
}