package com.example.tweetsy.repository

import com.example.tweetsy.api.TweetsyAPI
import com.example.tweetsy.models.TweetListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TweetRepository @Inject constructor(private val tweetsyAPI: TweetsyAPI){
    private val _categories = MutableStateFlow<List<String>>(emptyList())  // only the owner can change the value
    val categories: StateFlow<List<String>>
        get() = _categories

    private val _tweets = MutableStateFlow<List<TweetListItem>>(emptyList())
    val tweets: StateFlow<List<TweetListItem>>
        get() = _tweets


    suspend fun getCategories() {
        val result = tweetsyAPI.getCategories()
        if (result.isSuccessful && result.body() != null) {
            _categories.emit(result.body()!!)
        }
    }

    suspend fun getTweets(category: String) {
        val result = tweetsyAPI.getTweets("tweets[?(@.category==\"$category\")]") // the value come from viewlayer pass into this
        if (result.isSuccessful && result.body() != null) {
            _tweets.emit(result.body()!!)
        }
    }

}