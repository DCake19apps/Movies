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
        println("discover_debug Retriever: get")
        if (channel.isClosedForReceive) {
            println("discover_debug Retriever: channel is closed")
            channel = Channel()
        }

        if (job?.isActive != true) {
            println("discover_debug Retriever: job is not active")
            job = CoroutineScope(dispatcher).launch(CoroutineExceptionHandler {
                    coroutineContext, throwable ->
                println("discover_debug Retriever exception: ${throwable.message}")
                channel.cancel(CancellationException(null, throwable))
            }) {
                val t = retrieve()
                println("discover_debug Retriever: retrieve")
                channel.let {
                    if (!it.isClosedForSend) {
                        println("discover_debug Retriever: channel is not closed for send")
                        it.send(t)
                        it.close()
                    }
                }
            }
        }

        return coroutineScope {
            coroutineContext.job.invokeOnCompletion {
                println("discover_debug Retriever: channel close ${it?.message}")
                channel.close()
            }
            println("discover_debug Retriever: channel receive")
            channel.receive()
        }
    }

}