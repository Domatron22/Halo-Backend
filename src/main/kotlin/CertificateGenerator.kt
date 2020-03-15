package main.kotlin

import io.ktor.network.tls.certificates.generateCertificate
import java.io.File

/* Author: Dominic Triano
 * Date: 2/23/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 *
 * Generates a Certificate that Ktor requires on start up
 *
 */

object CertificateGenerator {
    @JvmStatic
    fun main(args: Array<String>) {
        val jksFile = File("build/temporary.jks").apply {
            parentFile.mkdirs() //creates the file path to store the keys (application.conf)
        }

        if (!jksFile.exists()) {
            generateCertificate(jksFile) // Generates the certificate
        }
    }
}