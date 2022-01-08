package com.mateuslima.blocodenotas.feature_notas.presentation.ui.home_notes

import android.content.Intent
import android.provider.MediaStore
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.presentation.adapter.NotasAdapter
import com.mateuslima.blocodenotas.feature_notas.presentation.ui.main.MainActivity
import com.mateuslima.blocodenotas.feature_notas.presentation.ui.selecao_foto.SelecaoFotoFragment
import com.mateuslima.blocodenotas.launchFragmentInHiltContainer
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeNotesFragmentTest{

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        Intents.init()
    }

    @After
    fun teardown(){
        Intents.release()
    }

    @Test
    fun onClickFotoPerfil_validarIntentCamera(){
        val intentCamera = allOf(
            hasAction(MediaStore.ACTION_IMAGE_CAPTURE)
        )
        onView(withId(R.id.image_perfil)).perform(click())
        onView(withId(R.id.text_camera)).check(matches(isDisplayed()))
        onView(withId(R.id.text_camera)).perform(click())
        Intents.intended(intentCamera)
    }

    @Test
    fun onClickFotoPerfil_validarIntentGaleria(){
        val intentGaleria = allOf(
            hasAction(Intent.ACTION_PICK),
            hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        )
        onView(withId(R.id.image_perfil)).perform(click())
        onView(withId(R.id.text_galeria)).check(matches(isDisplayed()))
        onView(withId(R.id.text_galeria)).perform(click())
        Intents.intended(intentGaleria)
    }

    @Test
    fun onClickFotoPerfil_validarIrParaSelecaoFotoFragment(){
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<HomeNotesFragment> {
            navController.setGraph(R.navigation.nav_main)
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.image_perfil)).perform(click())
        onView(withId(R.id.text_internet)).check(matches(isDisplayed()))
        onView(withId(R.id.text_internet)).perform(click())

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.selecaoFotoFragment)
    }

    @Test
    fun onClickFabAddNota_irParaAddEditNotasFragment(){
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<HomeNotesFragment> {
            navController.setGraph(R.navigation.nav_main)
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.fab_add)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.addEditNotasFragment)
    }

}