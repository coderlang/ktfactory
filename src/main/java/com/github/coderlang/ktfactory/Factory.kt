package com.github.coderlang.ktfactory

import kotlin.reflect.full.createInstance

interface Factory {
	companion object
}

open class FactoryContainer {
	open val AllFactory: Map<String, String> = mapOf()
}

private val allFactory = lazy {
	val kClass = FactoryContainer::class.qualifiedName
		?.let { Class.forName(it + "Impl").kotlin }

	(kClass?.createInstance() as? FactoryContainer)?.AllFactory
}

fun <T> Factory.Companion.Get(key:String, factory:String=""): T? {
	val kClass = allFactory.value?.get("${factory}${key}")?.let {
		Class.forName(it).kotlin
	}

	return kClass?.createInstance() as T
}
