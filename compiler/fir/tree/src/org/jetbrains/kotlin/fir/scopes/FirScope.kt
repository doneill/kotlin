/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.scopes

import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol
import org.jetbrains.kotlin.name.Name

typealias ScopeProcessor<S> = (S) -> Unit

abstract class FirScope {
    open fun processClassifiersByName(
        name: Name,
        processor: ScopeProcessor<FirClassifierSymbol<*>>
    ) {}

    open fun processFunctionsByName(
        name: Name,
        processor: ScopeProcessor<FirFunctionSymbol<*>>
    ) {}

    open fun processPropertiesByName(
        name: Name,
        processor: ScopeProcessor<FirVariableSymbol<*>>
    ) {}
}

enum class ProcessorAction {
    STOP,
    NEXT,
    NONE;

    operator fun not(): Boolean {
        return when (this) {
            STOP -> true
            NEXT -> false
            NONE -> false
        }
    }

    fun stop() = this == STOP
    fun next() = this != STOP

    operator fun plus(other: ProcessorAction): ProcessorAction {
        if (this == NEXT || other == NEXT) return NEXT
        return this
    }
}
