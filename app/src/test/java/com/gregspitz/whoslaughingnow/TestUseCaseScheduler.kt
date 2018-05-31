package com.gregspitz.whoslaughingnow

/**
 * A scheduler that runs synchronously, for testing purposes.
 */
class TestUseCaseScheduler : UseCaseScheduler {
    override fun execute(runnable: Runnable) {
        runnable.run()
    }

    override fun <V : UseCase.ResponseValue> notifyResponse(
            response: V, useCaseCallback: UseCase.UseCaseCallback<V>) {
        useCaseCallback.onSuccess(response)
    }

    override fun <V : UseCase.ResponseValue> onError(useCaseCallback: UseCase.UseCaseCallback<V>) {
        useCaseCallback.onError()
    }

}
