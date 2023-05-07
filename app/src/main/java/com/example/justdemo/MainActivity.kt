package com.example.justdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var myObservable: Observable<String>
    private lateinit var myObserver: DisposableObserver<String>

    private lateinit var textView: TextView
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tvGreeting)
        myObservable = Observable.just("Hello A","Hello B", "Hello C")

        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<String> {

        myObserver = object : DisposableObserver<String>() {

            override fun onNext(s: String) {
                Log.d("MAN","onNext: $s")
                textView.text = s
            }

            override fun onError(e: Throwable) {
                Log.d("MAN","onError")
            }

            override fun onComplete() {
                Log.d("MAN","onComplete")
            }

        }

        return myObserver
    }
}