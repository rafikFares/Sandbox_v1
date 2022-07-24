package com.example.sightcall.core.interactor

import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.utils.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params?): Either<SightCallException, Type>

    operator fun invoke(
        params: Params?,
        scope: CoroutineScope,
        onResult: (Either<SightCallException, Type>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }
}
