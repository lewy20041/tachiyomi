package eu.kanade.presentation.more.settings.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import eu.kanade.domain.source.service.SourcePreferences
import eu.kanade.presentation.more.settings.Preference
import eu.kanade.tachiyomi.util.system.AuthenticatorUtil.authenticate
import tachiyomi.core.i18n.localize
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.i18n.localize
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

object SettingsBrowseScreen : SearchableSettings {

    @ReadOnlyComposable
    @Composable
    override fun getTitleRes() = MR.strings.browse

    @Composable
    override fun getPreferences(): List<Preference> {
        val context = LocalContext.current
        val sourcePreferences = remember { Injekt.get<SourcePreferences>() }
        return listOf(
            Preference.PreferenceGroup(
                title = localize(MR.strings.label_sources),
                preferenceItems = listOf(
                    Preference.PreferenceItem.SwitchPreference(
                        pref = sourcePreferences.hideInLibraryItems(),
                        title = localize(MR.strings.pref_hide_in_library_items),
                    ),
                ),
            ),
            Preference.PreferenceGroup(
                title = localize(MR.strings.pref_category_nsfw_content),
                preferenceItems = listOf(
                    Preference.PreferenceItem.SwitchPreference(
                        pref = sourcePreferences.showNsfwSource(),
                        title = localize(MR.strings.pref_show_nsfw_source),
                        subtitle = localize(MR.strings.requires_app_restart),
                        onValueChanged = {
                            (context as FragmentActivity).authenticate(
                                title = context.localize(MR.strings.pref_category_nsfw_content),
                            )
                        },
                    ),
                    Preference.PreferenceItem.InfoPreference(localize(MR.strings.parental_controls_info)),
                ),
            ),
        )
    }
}
