/*
 * Copyright (C) 2020 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package okio

import okio.internal.commonCompareTo
import okio.internal.commonEquals
import okio.internal.commonHashCode
import okio.internal.commonIsAbsolute
import okio.internal.commonIsRelative
import okio.internal.commonIsRoot
import okio.internal.commonName
import okio.internal.commonNameBytes
import okio.internal.commonParent
import okio.internal.commonResolve
import okio.internal.commonToPath
import okio.internal.commonToString
import okio.internal.commonVolumeLetter
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
import java.io.File
import java.nio.file.Paths
import java.nio.file.Path as NioPath

@ExperimentalFileSystem
actual class Path internal actual constructor(
  internal actual val bytes: ByteString
) : Comparable<Path> {
  actual val isAbsolute: Boolean
    get() = commonIsAbsolute()

  actual val isRelative: Boolean
    get() = commonIsRelative()

  @get:JvmName("volumeLetter")
  actual val volumeLetter: Char?
    get() = commonVolumeLetter()

  @get:JvmName("nameBytes")
  actual val nameBytes: ByteString
    get() = commonNameBytes()

  @get:JvmName("name")
  actual val name: String
    get() = commonName()

  @get:JvmName("parent")
  actual val parent: Path?
    get() = commonParent()

  actual val isRoot: Boolean
    get() = commonIsRoot()

  @JvmName("resolve")
  actual operator fun div(child: String): Path = commonResolve(child)

  @JvmName("resolve")
  actual operator fun div(child: Path): Path = commonResolve(child)

  fun toFile(): File = File(toString())

  @IgnoreJRERequirement // Can only be invoked on platforms that have java.nio.file.
  fun toNioPath(): NioPath = Paths.get(toString())

  actual override fun compareTo(other: Path): Int = commonCompareTo(other)

  actual override fun equals(other: Any?): Boolean = commonEquals(other)

  actual override fun hashCode() = commonHashCode()

  actual override fun toString() = commonToString()

  actual companion object {
    @JvmField
    actual val DIRECTORY_SEPARATOR: String = File.separator

    @JvmName("get") @JvmStatic
    actual fun String.toPath(): Path = commonToPath()

    @JvmName("get") @JvmStatic
    fun File.toOkioPath(): Path = toString().toPath()

    @JvmName("get") @JvmStatic
    @IgnoreJRERequirement // Can only be invoked on platforms that have java.nio.file.
    fun NioPath.toOkioPath(): Path = toString().toPath()
  }
}
