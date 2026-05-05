/**
 * CarFix Rwanda — Theme & Language Controller v3
 * No defer timing issues: functions are global as soon as script runs.
 * applyTheme() and applyLanguage() are called immediately at script end.
 */

// ─────────────────────────────────────────────
// TRANSLATIONS
// ─────────────────────────────────────────────
var TRANSLATIONS = {
    en: {
        'nav.home': 'Home',
        'nav.find_mechanics': 'Find mechanics',
        'nav.help': 'Help',
        'nav.my_vehicles': 'My vehicles',
        'nav.new_request': 'New request',
        'nav.dashboard': 'Dashboard',
        'nav.directory': 'Directory',
        'nav.admin': 'Admin',
        'nav.my_dashboard': 'My dashboard',
        'nav.sign_in': 'Sign in',
        'nav.register': 'Register',
        'nav.log_out': 'Log out',

        'hero.headline': 'Expert care for your car, <span>anywhere</span> in Rwanda.',
        'hero.subtitle': 'Find verified mechanics by specialization and vehicle type, submit service requests, and track status from one simple place.',
        'hero.find_mechanic': 'Find a mechanic &rarr;',
        'hero.join_mechanic': 'Join as a mechanic',
        'hero.search_title': 'Search mechanics',
        'hero.search_note': 'Runs a real search on the public directory (verified garages only).',
        'hero.spec_label': 'Specialization',
        'hero.spec_placeholder': 'e.g. Brakes, engine',
        'hero.model_label': 'Vehicle brand / model',
        'hero.model_placeholder': 'e.g. Toyota RAV4',
        'hero.kw_label': 'Keyword (optional)',
        'hero.kw_placeholder': 'Area, garage name',
        'hero.search_btn': 'Search',
        'hero.search_warn': 'Enter at least one search field before searching.',

        'feat.tag': 'OUR EDGE',
        'feat.title': 'Why CarFix Rwanda?',
        'feat.sm_title': 'Specialized mechanics',
        'feat.sm_body': 'Browse profiles with clear specialties and supported vehicle types before you book.',
        'feat.vl_title': 'Verified listings',
        'feat.vl_body': 'Mechanics in the public directory are approved by an administrator after registration.',
        'feat.rt_title': 'Request tracking',
        'feat.rt_body': 'Customers follow request status; mechanics update progress from their dashboard.',

        'hiw.title': 'How it works',
        'hiw.subtitle': 'Three steps from search to tracked service.',
        'hiw.step1_title': 'Search',
        'hiw.step1_body': 'Use filters on the mechanics page to find a garage that fits your vehicle and repair need.',
        'hiw.step2_title': 'Request',
        'hiw.step2_body': 'Sign in as a customer, register your vehicle, and submit a service request.',
        'hiw.step3_title': 'Track',
        'hiw.step3_body': 'Watch status updates on your dashboard. Admins can assign a mechanic if you did not pick one.',

        'cta.title': 'Ready to find the right mechanic?',
        'cta.browse': 'Browse directory',
        'cta.help': 'Help &amp; contact',

        'footer.brand_body': 'A practical platform for matching vehicle owners with mechanics by specialization and vehicle support.',
        'footer.platform': 'Platform',
        'footer.create_account': 'Create account',
        'footer.sign_in': 'Sign in',
        'footer.help': 'Help',
        'footer.support': 'Support',
        'footer.help_center': 'Help center',
        'footer.find_mechanic': 'Find a mechanic',
        'footer.get_started': 'Get started',
        'footer.get_started_body': 'Register as a customer or mechanic, then use the matching login tab.',
        'footer.copy': '&copy; 2026 CarFix Rwanda',

        'login.back': '&larr; Back to home',
        'login.tab_customer': 'Customer',
        'login.tab_mechanic': 'Mechanic',
        'login.tab_admin': 'Admin',
        'login.customer_title': 'Customer Login',
        'login.customer_desc': 'Sign in to book repairs and manage your vehicles.',
        'login.error': 'Invalid email or password.',
        'login.logout_ok': 'You have been logged out successfully.',
        'login.label_email': 'Email',
        'login.placeholder_email': 'Enter your email',
        'login.label_password': 'Password',
        'login.placeholder_password': 'Enter your password',
        'login.submit': 'Sign In',
        'login.no_account': "Don't have an account?",
        'login.create_one': 'Create one',

        'reg.back': '&larr; Back to home',
        'reg.title': 'Create account',
        'reg.subtitle': 'Choose <strong>Customer</strong> to book services, or <strong>Mechanic</strong> to list your garage.',
        'reg.tab_customer': 'Customer',
        'reg.tab_mechanic': 'Mechanic',
        'reg.label_name': 'Full name',
        'reg.placeholder_name': 'Your full name',
        'reg.label_email': 'Email',
        'reg.placeholder_email': 'you@example.com',
        'reg.label_password': 'Password',
        'reg.placeholder_password': 'Create a secure password',
        'reg.label_phone': 'Phone number',
        'reg.placeholder_phone': 'e.g. 0780000000',
        'reg.mech_hint': 'Mechanic accounts need a short garage profile. An administrator will review and approve your listing.',
        'reg.label_spec': 'Specialization',
        'reg.placeholder_spec': 'e.g. Engine repair, brakes',
        'reg.label_vehicles': 'Supported vehicle models / brands',
        'reg.placeholder_vehicles': 'e.g. Toyota, BMW, Volkswagen',
        'reg.label_location': 'Garage location',
        'reg.placeholder_location': 'e.g. Kigali, Remera',
        'reg.submit': 'Create account',
        'reg.have_account': 'Already have an account?',
        'reg.sign_in': 'Sign in',

        'dash.overview': 'Overview',
        'dash.my_vehicles': 'My Vehicles',
        'dash.add_vehicle': 'Add Vehicle',
        'dash.my_requests': 'My Service Requests',
        'dash.new_request': 'Create New',
        'dash.my_invoices': 'My Invoices',
        'dash.service_progress': 'Service Progress',
        'dash.recommended': 'Recommended Mechanics',
        'dash.log_out': 'Log out',
        'dash.welcome': 'Welcome back',
        'dash.my_jobs': 'My jobs',
        'dash.directory': 'Directory',
        'dash.refresh': 'Refresh',
        'dash.assigned_jobs': 'Your service requests',
        'dash.generated_invoices': 'Generated Invoices',
        'dash.assigned_to_you': 'ASSIGNED TO YOU',
        'dash.open_jobs': 'OPEN (NOT DONE)',
        'dash.completed_jobs': 'COMPLETED'
    },

    fr: {
        'nav.home': 'Accueil',
        'nav.find_mechanics': 'Trouver des mécaniciens',
        'nav.help': 'Aide',
        'nav.my_vehicles': 'Mes véhicules',
        'nav.new_request': 'Nouvelle demande',
        'nav.dashboard': 'Tableau de bord',
        'nav.directory': 'Annuaire',
        'nav.admin': 'Admin',
        'nav.my_dashboard': 'Mon tableau de bord',
        'nav.sign_in': 'Se connecter',
        'nav.register': "S'inscrire",
        'nav.log_out': 'Se déconnecter',

        'hero.headline': 'Des soins experts pour votre voiture, <span>partout</span> au Rwanda.',
        'hero.subtitle': 'Trouvez des mécaniciens vérifiés par spécialité et type de véhicule, soumettez des demandes et suivez leur état.',
        'hero.find_mechanic': 'Trouver un mécanicien &rarr;',
        'hero.join_mechanic': 'Rejoindre comme mécanicien',
        'hero.search_title': 'Rechercher des mécaniciens',
        'hero.search_note': 'Recherche en temps réel dans le répertoire public (garages vérifiés uniquement).',
        'hero.spec_label': 'Spécialisation',
        'hero.spec_placeholder': 'ex. Freins, moteur',
        'hero.model_label': 'Marque / modèle du véhicule',
        'hero.model_placeholder': 'ex. Toyota RAV4',
        'hero.kw_label': 'Mot-clé (optionnel)',
        'hero.kw_placeholder': 'Zone, nom du garage',
        'hero.search_btn': 'Rechercher',
        'hero.search_warn': 'Veuillez remplir au moins un champ avant de rechercher.',

        'feat.tag': 'NOS ATOUTS',
        'feat.title': 'Pourquoi CarFix Rwanda ?',
        'feat.sm_title': 'Mécaniciens spécialisés',
        'feat.sm_body': 'Parcourez les profils avec spécialités et types de véhicules pris en charge avant de réserver.',
        'feat.vl_title': 'Annonces vérifiées',
        'feat.vl_body': "Les mécaniciens du répertoire sont approuvés par un administrateur après l'inscription.",
        'feat.rt_title': 'Suivi des demandes',
        'feat.rt_body': 'Les clients suivent le statut ; les mécaniciens mettent à jour la progression depuis leur tableau de bord.',

        'hiw.title': 'Comment ça marche',
        'hiw.subtitle': 'Trois étapes de la recherche au service suivi.',
        'hiw.step1_title': 'Rechercher',
        'hiw.step1_body': 'Utilisez les filtres pour trouver un garage adapté à votre véhicule.',
        'hiw.step2_title': 'Demander',
        'hiw.step2_body': 'Connectez-vous, enregistrez votre véhicule et soumettez une demande de service.',
        'hiw.step3_title': 'Suivre',
        'hiw.step3_body': "Suivez les mises à jour sur votre tableau de bord.",

        'cta.title': 'Prêt à trouver le bon mécanicien ?',
        'cta.browse': 'Parcourir le répertoire',
        'cta.help': 'Aide &amp; contact',

        'footer.brand_body': 'Une plateforme pratique pour mettre en relation propriétaires de véhicules et mécaniciens.',
        'footer.platform': 'Plateforme',
        'footer.create_account': 'Créer un compte',
        'footer.sign_in': 'Se connecter',
        'footer.help': 'Aide',
        'footer.support': 'Support',
        'footer.help_center': "Centre d'aide",
        'footer.find_mechanic': 'Trouver un mécanicien',
        'footer.get_started': 'Commencer',
        'footer.get_started_body': "Inscrivez-vous en tant que client ou mécanicien, puis utilisez l'onglet correspondant.",
        'footer.copy': '&copy; 2026 CarFix Rwanda',

        'login.back': "&larr; Retour à l'accueil",
        'login.tab_customer': 'Client',
        'login.tab_mechanic': 'Mécanicien',
        'login.tab_admin': 'Admin',
        'login.customer_title': 'Connexion Client',
        'login.customer_desc': 'Connectez-vous pour réserver des réparations et gérer vos véhicules.',
        'login.error': 'Email ou mot de passe incorrect.',
        'login.logout_ok': 'Vous avez été déconnecté avec succès.',
        'login.label_email': 'E-mail',
        'login.placeholder_email': 'Entrez votre e-mail',
        'login.label_password': 'Mot de passe',
        'login.placeholder_password': 'Entrez votre mot de passe',
        'login.submit': 'Se connecter',
        'login.no_account': "Vous n'avez pas de compte ?",
        'login.create_one': 'En créer un',

        'reg.back': "&larr; Retour à l'accueil",
        'reg.title': 'Créer un compte',
        'reg.subtitle': 'Choisissez <strong>Client</strong> pour réserver des services, ou <strong>Mécanicien</strong> pour lister votre garage.',
        'reg.tab_customer': 'Client',
        'reg.tab_mechanic': 'Mécanicien',
        'reg.label_name': 'Nom complet',
        'reg.placeholder_name': 'Votre nom complet',
        'reg.label_email': 'E-mail',
        'reg.placeholder_email': 'vous@exemple.com',
        'reg.label_password': 'Mot de passe',
        'reg.placeholder_password': 'Créez un mot de passe sécurisé',
        'reg.label_phone': 'Numéro de téléphone',
        'reg.placeholder_phone': 'ex. 0780000000',
        'reg.mech_hint': "Les comptes mécaniciens nécessitent un profil garage. Un administrateur examinera et approuvera votre annonce.",
        'reg.label_spec': 'Spécialisation',
        'reg.placeholder_spec': 'ex. Réparation moteur, freins',
        'reg.label_vehicles': 'Modèles / marques pris en charge',
        'reg.placeholder_vehicles': 'ex. Toyota, BMW, Volkswagen',
        'reg.label_location': 'Emplacement du garage',
        'reg.placeholder_location': 'ex. Kigali, Remera',
        'reg.submit': 'Créer un compte',
        'reg.have_account': 'Vous avez déjà un compte ?',
        'reg.sign_in': 'Se connecter',

        'dash.overview': 'Aperçu',
        'dash.my_vehicles': 'Mes Véhicules',
        'dash.add_vehicle': 'Ajouter Véhicule',
        'dash.my_requests': 'Mes Demandes',
        'dash.new_request': 'Nouvelle',
        'dash.my_invoices': 'Mes Factures',
        'dash.service_progress': 'Progression du Service',
        'dash.recommended': 'Mécaniciens Recommandés',
        'dash.log_out': 'Se déconnecter',
        'dash.welcome': 'Bon retour',
        'dash.my_jobs': 'Mes travaux',
        'dash.directory': 'Annuaire',
        'dash.refresh': 'Actualiser',
        'dash.assigned_jobs': 'Vos demandes de service',
        'dash.generated_invoices': 'Factures Générées',
        'dash.assigned_to_you': 'VOUS SONT ASSIGNÉS',
        'dash.open_jobs': 'OUVERT (NON TERMINÉ)',
        'dash.completed_jobs': 'TERMINÉ'
    },

    rw: {
        'nav.home': 'Ahabanza',
        'nav.find_mechanics': 'Shaka mekaniki',
        'nav.help': 'Ubufasha',
        'nav.my_vehicles': 'Imodoka zanjye',
        'nav.new_request': 'Icyifuzo gishya',
        'nav.dashboard': 'Ikibaho',
        'nav.directory': 'Urutonde',
        'nav.admin': 'Ubuyobozi',
        'nav.my_dashboard': 'Ikibaho cyanjye',
        'nav.sign_in': 'Injira',
        'nav.register': 'Iyandikishe',
        'nav.log_out': 'Sohoka',

        'hero.headline': 'Serivisi nziza ku modoka yawe, <span>aho utuye</span> mu Rwanda.',
        'hero.subtitle': "Shaka mekaniki bizewe hashingiwe ku buhanga no moko y'imodoka, ohereze ibisabwa, ukurikirane aho bigeze.",
        'hero.find_mechanic': 'Shaka mekaniki &rarr;',
        'hero.join_mechanic': "Iyandikishe nk'imekaniki",
        'hero.search_title': 'Shakisha mekaniki',
        'hero.search_note': 'Iperereza rigezweho mu rutonde rwa rubanda (inganda zemewe gusa).',
        'hero.spec_label': 'Ubuhanga',
        'hero.spec_placeholder': 'urugero: Büreki, moteri',
        'hero.model_label': "Icapacito / modeli y'imodoka",
        'hero.model_placeholder': 'urugero: Toyota RAV4',
        'hero.kw_label': 'Ijambo ngenderwaho (ntibigomba)',
        'hero.kw_placeholder': "Akarere, izina ry'inganda",
        'hero.search_btn': 'Shakisha',
        'hero.search_warn': 'Uzuza nibura inzego imwe mbere yo gushakisha.',

        'feat.tag': 'IBIRANGA',
        'feat.title': 'Kuki CarFix Rwanda?',
        'feat.sm_title': 'Mekaniki inzobere',
        'feat.sm_body': "Reba profili z'ubuhanga busobanutse n'imoko y'imodoka zikoreshwa mbere yo gufata ibyemezo.",
        'feat.vl_title': 'Urutonde rwemejwe',
        'feat.vl_body': "Imekaniki ziri mu rutonde zemejwe n'ubuyobozi nyuma yo kwiyandikisha.",
        'feat.rt_title': 'Gukurikirana ibisabwa',
        'feat.rt_body': 'Abakiriya bakurikirana aho bisabwa bigeze; imekaniki ihindura aho akazi kegeze.',

        'hiw.title': 'Uburyo bikora',
        'hiw.subtitle': 'Inzira eshatu kuva gushakisha kugeza serivisi ikurikirana.',
        'hiw.step1_title': 'Shakisha',
        'hiw.step1_body': "Koresha ingano ku ipaji y'imekaniki kugirango ubonе inganda ikwiranye.",
        'hiw.step2_title': 'Saba',
        'hiw.step2_body': "Injira nk'umukiriya, andika imodoka yawe, wohereze icyifuzo.",
        'hiw.step3_title': 'Kurikirana',
        'hiw.step3_body': 'Kureba uko bikora ku kibaho cyawe. Ubuyobozi bushobora guha mekaniki.',

        'cta.title': 'Witeguye kubona mekaniki ikwiriye?',
        'cta.browse': 'Reba urutonde',
        'cta.help': 'Ubufasha &amp; gutumanahana',

        'footer.brand_body': "Urubuga rugamije guhuza inzobere z'imodoka n'abagenzi.",
        'footer.platform': 'Urubuga',
        'footer.create_account': 'Fungura konti',
        'footer.sign_in': 'Injira',
        'footer.help': 'Ubufasha',
        'footer.support': 'Inkunga',
        'footer.help_center': "Ikigo cy'ubufasha",
        'footer.find_mechanic': 'Shaka mekaniki',
        'footer.get_started': 'Tangira',
        'footer.get_started_body': "Iyandikishe nk'umukiriya cyangwa mekaniki, hanyuma ukoreshe tab ikwiriye yo kwinjira.",
        'footer.copy': '&copy; 2026 CarFix Rwanda',

        'login.back': '&larr; Subira ahabanza',
        'login.tab_customer': 'Umukiriya',
        'login.tab_mechanic': 'Mekaniki',
        'login.tab_admin': 'Ubuyobozi',
        'login.customer_title': "Kwinjira nk'Umukiriya",
        'login.customer_desc': 'Injira kugira ngo ubone gufata ibyemezo no gucunga imodoka zawe.',
        'login.error': 'Imeli cyangwa ijambo banga si byo.',
        'login.logout_ok': 'Wasohowe neza.',
        'login.label_email': 'Imeli',
        'login.placeholder_email': 'Injiza imeli yawe',
        'login.label_password': 'Ijambo banga',
        'login.placeholder_password': 'Injiza ijambo banga ryawe',
        'login.submit': 'Injira',
        'login.no_account': 'Nufite konti?',
        'login.create_one': 'Fungura',

        'reg.back': '&larr; Subira ahabanza',
        'reg.title': 'Fungura konti',
        'reg.subtitle': 'Hitamo <strong>Umukiriya</strong> kugira ngo usabe serivisi, cyangwa <strong>Mekaniki</strong> kugira ngo wandike inganda yawe.',
        'reg.tab_customer': 'Umukiriya',
        'reg.tab_mechanic': 'Mekaniki',
        'reg.label_name': 'Amazina yuzuye',
        'reg.placeholder_name': 'Amazina yawe yuzuye',
        'reg.label_email': 'Imeli',
        'reg.placeholder_email': 'wewe@urugero.com',
        'reg.label_password': 'Ijambo banga',
        'reg.placeholder_password': 'Fungura ijambo banga rikomeye',
        'reg.label_phone': 'Nimero ya telefoni',
        'reg.placeholder_phone': 'urugero: 0780000000',
        'reg.mech_hint': "Konti z'imekaniki zisaba profili ngufi y'inganda. Umuyobozi azareba hanyuma wemere.",
        'reg.label_spec': 'Ubuhanga',
        'reg.placeholder_spec': 'urugero: Gukora moteri, büreki',
        'reg.label_vehicles': "Imoko / modeli z'imodoka zikoreshwa",
        'reg.placeholder_vehicles': 'urugero: Toyota, BMW, Volkswagen',
        'reg.label_location': 'Aho inganda iri',
        'reg.placeholder_location': 'urugero: Kigali, Remera',
        'reg.submit': 'Fungura konti',
        'reg.have_account': 'Usanzwe ufite konti?',
        'reg.sign_in': 'Injira',

        'dash.overview': 'Incamake',
        'dash.my_vehicles': 'Imodoka Zanjye',
        'dash.add_vehicle': 'Ongeramo Imodoka',
        'dash.my_requests': 'Ibisabwa Byanjye',
        'dash.new_request': 'Saba',
        'dash.my_invoices': 'Inyemezabwishyu Zanjye',
        'dash.service_progress': 'Aho Bigeze',
        'dash.recommended': 'Mekaniki Batoranyijwe',
        'dash.log_out': 'Sohoka',
        'dash.welcome': 'Murakaza neza',
        'dash.my_jobs': 'Imirimo yanjye',
        'dash.directory': 'Urutonde',
        'dash.refresh': 'Vugurura',
        'dash.assigned_jobs': 'Ibisabwa byawe',
        'dash.generated_invoices': 'Inyemezabwishyu Zakozwe',
        'dash.assigned_to_you': 'IBYOHEREJWE KURI WE',
        'dash.open_jobs': 'BIFUNGUYE',
        'dash.completed_jobs': 'BYARANGIYE'
    }
};

// ─────────────────────────────────────────────
// THEME
// ─────────────────────────────────────────────
function applyTheme(theme) {
    document.documentElement.setAttribute('data-theme', theme);
    localStorage.setItem('cfr-theme', theme);
    var btns = document.querySelectorAll('#themeToggleBtn');
    btns.forEach(function(btn) {
        btn.innerHTML     = theme === 'dark' ? '&#9728;&#65039;' : '&#127769;';
        btn.title         = theme === 'dark' ? 'Switch to Light Mode' : 'Switch to Dark Mode';
        btn.setAttribute('aria-label', theme === 'dark' ? 'Switch to light mode' : 'Switch to dark mode');
    });
}

function toggleTheme() {
    var current = document.documentElement.getAttribute('data-theme') || 'light';
    applyTheme(current === 'dark' ? 'light' : 'dark');
}

// ─────────────────────────────────────────────
// LANGUAGE
// ─────────────────────────────────────────────
function applyLanguage(lang) {
    if (!TRANSLATIONS[lang]) lang = 'en';
    document.documentElement.setAttribute('lang', lang);
    localStorage.setItem('cfr-lang', lang);

    document.querySelectorAll('[data-i18n]').forEach(function(el) {
        var key = el.getAttribute('data-i18n');
        var val = TRANSLATIONS[lang][key];
        if (val === undefined || val === null) return;
        if (el.tagName === 'INPUT' || el.tagName === 'TEXTAREA') {
            if (el.type === 'submit' || el.type === 'button') {
                el.value = val;
            } else {
                el.placeholder = val;
            }
        } else {
            el.innerHTML = val;
        }
    });

    // Mark active language button
    document.querySelectorAll('.lang-option').forEach(function(btn) {
        btn.classList.toggle('active', btn.getAttribute('data-lang-code') === lang);
    });

    // Page-specific hook (e.g. login role titles)
    if (typeof window._afterLangApply === 'function') {
        window._afterLangApply(lang);
    }
}

function switchLanguage(lang) {
    applyLanguage(lang);
}

// ─────────────────────────────────────────────
// INIT — called as soon as this script executes.
// With defer: DOM is already parsed, this runs before DOMContentLoaded.
// Also schedules a safety re-run after DOMContentLoaded in case of edge cases.
// ─────────────────────────────────────────────
function cfrInit() {
    // One-time migration: clear old forced-dark default
    if (!localStorage.getItem('cfr-v3')) {
        localStorage.removeItem('cfr-theme');
        localStorage.removeItem('cfr-theme-v2');
        localStorage.setItem('cfr-v3', '1');
    }

    var savedTheme = localStorage.getItem('cfr-theme') || 'light';
    var savedLang  = localStorage.getItem('cfr-lang')  || 'en';
    applyTheme(savedTheme);
    applyLanguage(savedLang);
}

// Run immediately (covers defer case where DOM is ready)
cfrInit();

// Safety net: also run when DOM is fully ready in case of fast-load edge cases
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', cfrInit);
} else {
    cfrInit(); // DOM already ready (e.g. script placed at end of body)
}
