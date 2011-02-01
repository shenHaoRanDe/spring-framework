/*
 * Copyright 2011 the original author or authors.
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

package org.springframework.instrument.classloading.websphere;

import java.lang.instrument.ClassFileTransformer;

import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * {@link LoadTimeWeaver} implementation for WebSphere instrumentable classloader.
 *
 * <p><b>NOTE:</b> Requires WebSphere Application Server version 7.0.0 or higher.
 *
 * @author Costin Leau
 * @since 3.1
 */
public class WebSphereLoadTimeWeaver implements LoadTimeWeaver {

	private final WebSphereClassLoaderAdapter classLoader;

	/**
	 * Create a new instance of the {@link WebSphereLoadTimeWeaver} class using
	 * the default {@link ClassLoader class loader}.
	 * @see org.springframework.util.ClassUtils#getDefaultClassLoader()
	 */
	public WebSphereLoadTimeWeaver() {
		this(ClassUtils.getDefaultClassLoader());
	}

	/**
	 * Create a new instance of the {@link WebSphereLoadTimeWeaver} class using
	 * the supplied {@link ClassLoader}.
	 * @param classLoader the <code>ClassLoader</code> to delegate to for
	 * weaving (must not be <code>null</code>)
	 */
	public WebSphereLoadTimeWeaver(ClassLoader classLoader) {
		Assert.notNull(classLoader, "ClassLoader must not be null");
		this.classLoader = new WebSphereClassLoaderAdapter(classLoader);
	}

	public void addTransformer(ClassFileTransformer transformer) {
		this.classLoader.addTransformer(transformer);
	}

	public ClassLoader getInstrumentableClassLoader() {
		return this.classLoader.getClassLoader();
	}

	public ClassLoader getThrowawayClassLoader() {
		return this.classLoader.getThrowawayClassLoader();
	}
}