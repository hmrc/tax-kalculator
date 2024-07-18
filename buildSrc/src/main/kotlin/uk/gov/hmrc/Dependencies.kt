/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.hmrc

object Dependencies {
    object Common {
        object Main {
            const val stdlib = "stdlib-common"
            const val klock = "com.soywiz.korlibs.klock:klock:${Versions.klockVersion}"
            const val kermit = "co.touchlab:kermit:${Versions.kermitVersion}"
        }
        object Test {
            const val common = "test-common"
            const val annotations = "test-annotations-common"
            const val junit = "io.kotlintest:kotlintest-runner-junit5:${Versions.junit5Version}"
            const val jupiter = "org.junit.jupiter:junit-jupiter-engine:${Versions.jupiterEngineVersion}"
        }
    }

    object JVM {
        object Main {
            const val stdlib = "stdlib"
        }
        object Test {
            const val test = "test"
            const val junit = "test-junit5"
            const val jupiter = "org.junit.jupiter:junit-jupiter-params:${Versions.jupiterEngineVersion}"
        }
    }

    object IOS {
        object Main {
            const val stdlib = "stdlib"
        }
    }
}

object Versions {
    const val klockVersion = "2.4.13"
    const val kermitVersion = "2.0.4"
    const val junit5Version = "3.4.2"
    const val jupiterEngineVersion = "5.10.3"
}
