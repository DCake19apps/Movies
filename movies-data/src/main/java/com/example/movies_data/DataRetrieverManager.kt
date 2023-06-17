package com.example.movies_data

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

class DataRetrieverManager<T> (private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private var channel = Channel<T>()
    private var job: Job? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun get(retrieve: suspend () -> T): T {
        if (channel.isClosedForReceive) {
            channel = Channel()
        }

        if (job?.isActive != true) {
            job = CoroutineScope(dispatcher).launch(CoroutineExceptionHandler {
                    coroutineContext, throwable ->
                channel.cancel(CancellationException(null, throwable))
            }) {
                val t = retrieve()

                channel.let {
                    if (!it.isClosedForSend) {
                        it.send(t)
                        it.close()
                    }
                }
            }
        }

        return coroutineScope {
            coroutineContext.job.invokeOnCompletion {
                channel.close()
            }
            channel.receive()
        }
    }

}