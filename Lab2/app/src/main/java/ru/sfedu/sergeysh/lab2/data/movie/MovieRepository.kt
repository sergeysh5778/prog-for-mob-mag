package ru.sfedu.sergeysh.lab2.data.movie

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class MovieRepository {

    private val baseUrl: String = "https://www.kinopoisk.ru"
    private val top250Url: String = "/lists/movies/top250/"
    private val httpsPrefix: String = "https:"

    suspend fun getMovieList(): List<MovieCard> {
        return withContext(Dispatchers.IO) {
            try {
                val doc: Document = Jsoup.connect("$baseUrl$top250Url").get()
//                val doc: Document = Jsoup.parse(top250html)
                val movieDivs: Elements = doc.select("div[data-tid='8a6cbb06']")

                movieDivs.map {
                    val posterImg: Element = it.selectFirst("img[data-tid='d813cf42']")!!
                    val mainDiv: Element = it.selectFirst("div.styles_main__Y8zDm")!!
                    val linkA: Element = mainDiv.selectFirst("a[data-tid='d4e8d214']")!!
                    val nameSpan: Element = mainDiv.selectFirst("span[data-tid='4502216a']")!!
                    val mainInfoSpan: Element = mainDiv.selectFirst("span[data-tid='31fba632']")!!
                    val additionalInfoSpan: Element =
                        mainDiv.selectFirst("span.desktop-list-main-info_truncatedText__IMQRP")!!

                    MovieCard(
                        linkA.attr("href"),
                        httpsPrefix + posterImg.attr("src"),
                        nameSpan.text(),
                        additionalInfoSpan.text().substringAfter("• ").substringBefore(' '),
                        mainInfoSpan.text().substringBeforeLast(',').substringAfterLast(' '),
                    )
                }
            } catch (ex: Exception) {
                listOf()
            }
        }
    }

    suspend fun getMovieDetails(movieUrl: String): MovieDetails {
        return withContext(Dispatchers.IO) {
            val doc: Document = Jsoup.connect("$baseUrl$movieUrl").get()
//            val doc: Document = Jsoup.parse(movie435html)
            val movieDiv: Element = doc.selectFirst("div[data-tid='3716659c']")!!

            val posterImg: Element = movieDiv.selectFirst("img.film-poster")!!
            val titleSpan: Element = movieDiv.selectFirst("span[data-tid='75209b22']")!!
            val descriptionP: Element = movieDiv.selectFirst("p[data-tid='bfd38da2']")!!
            val yearDiv: Element = movieDiv.selectFirst("div[data-tid='cfbe5a01']")!!
            val genresDiv: Element = movieDiv.select("div[data-tid='d5ff4cc']")[1]
            val actorsLis: Elements =
                movieDiv.selectFirst("div.styles_actors__wn_C4")!!.select("li")

            MovieDetails(
                movieUrl,
                httpsPrefix + posterImg.attr("src"),
                titleSpan.text().removeRange(titleSpan.text().length - 7, titleSpan.text().length),
                genresDiv.text(),
                yearDiv.text(),
                actorsLis.eachText().joinToString(", "),
                descriptionP.text(),
            )
        }
    }

    private val top250html: String = """
        <!doctype html>
        <html>

        <head>
          <meta name="viewport" content="width=device-width">
          <meta charset="utf-8">
          <title data-tid="57f72b5">250 лучших фильмов — Кинопоиск</title>
          <meta name="theme-color" content="#1f1f1f" data-tid="57f72b5">
          <meta name="apple-mobile-web-app-capable" content="yes" data-tid="57f72b5">
          <meta name="apple-mobile-web-app-status-bar-style" content="black" data-tid="57f72b5">
          <meta name="apple-mobile-web-app-title" content="Кинопоиск" data-tid="57f72b5">
          <meta name="apple-itunes-app" content="app-id=477718890, ct=kp-web, pt=214944, mt=8" data-tid="57f72b5">
          <meta name="application-name" content="Кинопоиск" data-tid="57f72b5">
          <meta property="fb:app_id" content="121953784483000" data-tid="57f72b5">
          <meta property="fb:pages" content="152308956519" data-tid="57f72b5">
          <meta property="og:site_name" content="Кинопоиск" data-tid="57f72b5">
          <meta name="msapplication-TileColor" content="#000" data-tid="57f72b5">
          <meta name="msapplication-TileImage"
            content="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-144.png"
            data-tid="57f72b5">
          <meta name="msapplication-config" content="//st.kp.yandex.net/public/xml/ieconfig.xml" data-tid="57f72b5">
          <link rel="mask-icon"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-16.svg"
            data-tid="57f72b5">
          <link rel="apple-touch-icon-precomposed"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/apple-favicon-152.png"
            data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-57.png" sizes="57x57"
            data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-76.png" sizes="76x76"
            data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-96.png" sizes="96x96"
            data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-120.png"
            sizes="120x120" data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-128.png"
            sizes="128x128" data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-144.png"
            sizes="144x144" data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-152.png"
            sizes="152x152" data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-180.png"
            sizes="180x180" data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-196.png"
            sizes="196x196" data-tid="57f72b5">
          <link rel="icon" href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-64.ico"
            data-tid="57f72b5">
          <link rel="search" type="application/opensearchdescription+xml" title="Поиск на kinopoisk.ru" href="/kp_search.xml"
            data-tid="57f72b5">
          <meta name="description"
            content="Рейтинг составлен по результатам голосования посетителей сайта. Любой желающий может принять в нем участие, проголосовав за свой любимый фильм."
            data-tid="57f72b5">
          <link type="application/rss+xml" rel="alternate" title="RSS" href="/news.rss" data-tid="57f72b5">
          <link rel="canonical" href="https://www.kinopoisk.ru/lists/movies/top250/" data-tid="57f72b5">
          <meta property="og:title" content="250 лучших фильмов — списки лучших фильмов и сериалов — Кинопоиск"
            data-tid="57f72b5">
          <meta property="twitter:title" content="250 лучших фильмов — списки лучших фильмов и сериалов — Кинопоиск"
            data-tid="57f72b5">
          <meta property="og:description"
            content="Рейтинг составлен по результатам голосования посетителей сайта. Любой желающий может принять в нем участие, проголосовав за свой любимый фильм."
            data-tid="57f72b5">
          <meta property="twitter:description"
            content="Рейтинг составлен по результатам голосования посетителей сайта. Любой желающий может принять в нем участие, проголосовав за свой любимый фильм."
            data-tid="57f72b5">
          <meta property="og:url" content="https://www.kinopoisk.ru/lists/movies/top250/" data-tid="57f72b5">
          <meta property="og:image"
            content="https://avatars.mds.yandex.net/get-kinopoisk-image/4303601/157dbdcb-8a4a-4364-b08c-922d4c61aeb5/1200x630"
            data-tid="57f72b5">
          <meta property="vk:image"
            content="https://avatars.mds.yandex.net/get-kinopoisk-image/4303601/157dbdcb-8a4a-4364-b08c-922d4c61aeb5/1200x630"
            data-tid="57f72b5">
          <meta name="twitter:image"
            content="https://avatars.mds.yandex.net/get-kinopoisk-image/4303601/157dbdcb-8a4a-4364-b08c-922d4c61aeb5/1200x630"
            data-tid="57f72b5">
          <meta property="og:image:width" content="1200" data-tid="57f72b5">
          <meta property="og:image:height" content="630" data-tid="57f72b5">
          <meta name="twitter:site" content="kinopoiskru" data-tid="57f72b5">
          <meta name="twitter:card" content="summary_large_image" data-tid="57f72b5">
          <meta property="og:type" content="website" data-tid="57f72b5">
          <meta name="next-head-count" content="44">
          <link rel="preload" as="script" href="https://yandex.ru/ads/system/context.js" crossorigin="anonymous"
            data-tid="db024bc5">
          <script src="https://yandex.ru/ads/system/context.js" async crossorigin="anonymous"
            data-config="{&amp;quot;COMBO_EXP&amp;quot;:0}" data-tid="3e4d64d9"></script>
          <link rel="manifest"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/pwa-manifests/production.json"
            crossorigin="anonymous" data-tid="74a9c6bc">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/c94162dd6edb9809.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/c94162dd6edb9809.css"
            crossorigin="anonymous" data-n-g="">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/9bb2cddd1d03953c.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/9bb2cddd1d03953c.css"
            crossorigin="anonymous" data-n-p="">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/c61b7c49e71dbd6e.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/c61b7c49e71dbd6e.css"
            crossorigin="anonymous" data-n-p="">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/9bdf470b4cabbbef.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/9bdf470b4cabbbef.css"
            crossorigin="anonymous" data-n-p="">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/d2ddd25e213814f0.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/d2ddd25e213814f0.css"
            crossorigin="anonymous">
          <noscript data-n-css=""></noscript>
        </head>

        <body class="body" data-tid="f8463833">
          <div id="__next" data-reactroot="">
            <div class="styles_root__hSE6c styles_promoPopover__QZb7t" data-tid="d504712e">
              <button type="button" class="styles_closeButton__LEexp"><span
                  class="styles_closeButtonIcon__w69HE"></span></button>
            </div>
            <div class="styles_root__goB3B" data-tid="ef426037">
              <div class="styles_root__BJH2_ styles_headerContainer__3KSNh" data-tid="1d1e4a8c">
                <header class="styles_root__jlo2L styles_header__0LyFn styles_rootDark__nQDyF" data-tid="63b05890">
                  <div class="header-navigation styles_navigation__S8raz styles_navigationDark__x0vdW">
                    <div class="styles_logoContainer__G47EP">
                      <div class="styles_root__jfc_s" data-tid="911e2f4d">
                        <div class="">
                          <button class="styles_root__coHaQ styles_burger__HcbjK" type="button" data-tid="8ed90190"><span
                              class="styles_icon__J3wes" style="color:#999999"></span></button>
                          <div class="styles_dropdown__MqT__ styles_dropdownOpen__mTn_V styles_dropdownDefault__KwZHT"
                            data-tid="585eb7c0">
                            <div class="styles_dropdownMenu__bouwC styles_dropdownMenu__c7FZ4 styles_dropdownMenuDark__NzGxR">
                              <div class="styles_navigationMenu__c_jLJ styles_root__RBtQR" data-tid="e500879d">
                                <a href="/" class="styles_root__7mPJN styles_darkThemeItem__E_aGY" data-tid="de7c6530"><img
                                    loading="lazy" class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/128809/4d6f5bd4e839b166859243f82e9fdeb3bc910931/svg"
                                    data-tid="b35288c1">Главная</a><a href="https://hd.kinopoisk.ru/"
                                  class="styles_root__7mPJN styles_darkThemeItem__E_aGY" data-tid="de7c6530"><img loading="lazy"
                                    class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/61205/478c72b68bc4ac507483b2676994bbc1df5f05be/svg"
                                    data-tid="b35288c1">Онлайн-кинотеатр</a><a href="/lists/categories/movies/1/"
                                  class="styles_root__7mPJN styles_darkThemeItem__E_aGY" data-tid="de7c6530"><img loading="lazy"
                                    class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/50064/ab24b8099cb4ca11c08b0def91dc5c1d4fd78649/svg"
                                    data-tid="b35288c1">Фильмы</a><a href="/lists/categories/movies/3/"
                                  class="styles_root__7mPJN styles_darkThemeItem__E_aGY" data-tid="de7c6530"><img loading="lazy"
                                    class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/61205/9daeaf410906b5794685b7b5bb25dfd2c647fccf/svg"
                                    data-tid="b35288c1">Сериалы</a><a href="/afisha/new/"
                                  class="styles_root__7mPJN styles_darkThemeItem__E_aGY" data-tid="de7c6530"><img loading="lazy"
                                    class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/118781/ae7fbfc1773a6bbd61ee0154628c6fe14bf6959e/svg"
                                    data-tid="b35288c1">Билеты в кино</a><a href="/media/"
                                  class="styles_root__7mPJN styles_darkThemeItem__E_aGY" data-tid="de7c6530"><img loading="lazy"
                                    class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/118781/960a47a181b1b0a28ceb45a075e64c1a9378442c/svg"
                                    data-tid="b35288c1">Медиа</a>
                              </div>
                            </div>
                          </div>
                        </div><a href="/" class="styles_root__dYidr styles_logo___bcop" data-tid="d4e8d214"><img
                            class="styles_img__3hWmL kinopoisk-header-logo__img" alt="Кинопоиск"
                            src="data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTY0IiBoZWlnaHQ9IjM2IiBmaWxsPSJub25lIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPjxwYXRoIGZpbGwtcnVsZT0iZXZlbm9kZCIgY2xpcC1ydWxlPSJldmVub2RkIiBkPSJNNTguODU5IDE4YzAtNS44ODkgMi45NTQtMTAuNiA4LjI4MS0xMC42IDUuMzI4IDAgOC4yODEgNC43MTEgOC4yODEgMTAuNiAwIDUuODktMi45NTQgMTAuNi04LjI4IDEwLjYtNS4zMjggMC04LjI4Mi00LjcxLTguMjgyLTEwLjZabTguMjgxIDcuNjZjMi4wNzIgMCAyLjk1NC0zLjUzNCAyLjk1NC03LjY1MiAwLTQuMTItLjg4OS03LjY1Mi0yLjk1NC03LjY1Mi0yLjA2NSAwLTIuOTU0IDMuNTMzLTIuOTU0IDcuNjUyLS4wMDcgNC4xMTguODgyIDcuNjUyIDIuOTU0IDcuNjUyWk0zLjg0MyA3Ljd2NS41OTZoLjI5NEw3Ljk4IDcuN2g1LjMybC03LjA5OCA2LjQ3NC4yOTQuMjkzTDE5LjUxIDcuNjkzdjQuNzExTDcuOTczIDE2LjUyM3YuMjkybDExLjUzNy0xLjAyOHY0LjQxOUw3Ljk3MyAxOS4xNzh2LjI5M2wxMS41MzcgNC4xMTh2NC43MTJMNi40OTYgMjEuNTI2bC0uMjk0LjI5MyA3LjA5OCA2LjQ3NEg3Ljk4bC0zLjg0My01LjU5NmgtLjI5NHY1LjU5NkgwVjcuNjg2aDMuODQzVjcuN1ptMTkuMjMgMEgyOC4xbC0uMjk0IDEyLjM2M2guMjk0TDM0LjAxNSA3LjdoNC40Mzh2MjAuNjA4aC01LjAyNmwuMjk0LTEyLjM2NGgtLjI5NEwyNy41MSAyOC4zMDloLTQuNDM4VjcuN1ptMjMuOTU1IDBoLTUuMDI2djIwLjYwOGg1LjAyNnYtOS4xM2g0LjEzN3Y5LjEzaDUuMDI2VjcuN2gtNS4wMjZ2Ny45NTJoLTQuMTM3VjcuN1ptNDUuMjUgMGgtMTQuMTl2MjAuNjA4aDUuMDI3VjExLjIzM2g0LjEzN3YxNy4wNzVoNS4wMjZWNy43Wm0yLjY2IDEwLjNjMC01Ljg4OSAyLjk1NC0xMC42IDguMjgyLTEwLjYgNS4zMiAwIDguMjgxIDQuNzExIDguMjgxIDEwLjYgMCA1Ljg5LTIuOTU0IDEwLjYtOC4yODEgMTAuNi01LjMyIDAtOC4yODItNC43MS04LjI4Mi0xMC42Wm04LjI4MiA3LjY2YzIuMDcyIDAgMi45NTQtMy41MzQgMi45NTQtNy42NTIgMC00LjEyLS44ODktNy42NTItMi45NTQtNy42NTItMi4wNzIgMC0yLjk1NCAzLjUzMy0yLjk1NCA3LjY1MiAwIDQuMTE4Ljg4MiA3LjY1MiAyLjk1NCA3LjY1MlpNMTE5LjE4NyA3LjdoLTUuMDI2djIwLjYwOGg0LjQzOGw1LjkxNi0xMi4zNjRoLjI5NGwtLjI5NCAxMi4zNjRoNS4wMjZWNy43aC00LjQzOGwtNS45MTYgMTIuMzYzaC0uMjk0bC4yOTQtMTIuMzYzWm0yMy42NjkgMTMuNTQxIDQuNzMyLjU4NWMtLjg4OSA0LjEyLTIuOTU0IDYuNzc0LTcuMzY0IDYuNzc0LTUuMzIgMC04LjAxNi00LjcxLTguMDE2LTEwLjYgMC01Ljg4OSAyLjY4OS0xMC42IDguMDE2LTEwLjYgNC4zMTcgMCA2LjQ3NSAyLjY0OSA3LjM2NCA2LjQ3NWwtNC43MzIgMS4xNzdjLS4yOTQtMi4wNjMtMS4xNTUtNC43MS0yLjYzMi00LjcxLTEuNzcxIDAtMi42ODkgMy41MzMtMi42ODkgNy42NTEgMCA0LjA5LjkxOCA3LjY1MiAyLjY4OSA3LjY1MiAxLjQ0OS4wMTUgMi4zMy0yLjM0MSAyLjYzMi00LjQwNFptMTEuODMtMTMuNTRoLTQuNzMydjIwLjYwN2g0LjczMnYtOS4xM2guMjk0bDMuNTQ5IDkuMTNIMTY0bC01LjE3Ny0xMC42TDE2My44NDkgNy43aC01LjAyNmwtMy44NDMgOS4xM2gtLjI5NFY3LjdaIiBmaWxsPSIjZmZmIi8+PC9zdmc+Cg=="
                            data-tid="79e4350"></a>
                      </div>
                    </div>
                    <div class="styles_mainContainer__faOVn">
                      <div class="styles_featureMenuContainer__KbrzA">
                        <nav class="styles_root__DR_oz kinopoisk-header-featured-menu styles_adaptive__F508O"
                          data-tid="78f04c5d">
                          <a class="styles_root__hBoYg styles_item__HaqiK kinopoisk-header-featured-menu__item"
                            href="https://hd.kinopoisk.ru/" target="_self" data-tid="acc26a70"><img aria-label="presentation"
                              class="styles_iconHover__UMGd0 styles_icon__IXP4s"
                              src="https://avatars.mds.yandex.net/get-bunker/118781/5e4a451dabd5982b775db20bc084cc215fd0e14a/svg"
                              srcset="" data-tid="b35288c1"><img aria-label="presentation" class="styles_icon__IXP4s"
                              src="https://avatars.mds.yandex.net/get-bunker/61205/70cc2c1c559189c3139a315b1d06db38faefa2b5/svg"
                              srcset="" data-tid="b35288c1">Онлайн-кинотеатр</a><a
                            class="styles_root__hBoYg styles_item__HaqiK kinopoisk-header-featured-menu__item"
                            href="https://www.kinopoisk.ru/special/smarttv_instruction?utm_source=kinopoisk&amp;utm_medium=selfpromo_kp&amp;utm_campaign=button_header"
                            target="_blank" data-tid="acc26a70"><img aria-label="presentation"
                              class="styles_iconHover__UMGd0 styles_icon__IXP4s"
                              src="https://avatars.mds.yandex.net/get-bunker/50064/9bd69a8ca16bd1fa7395dba2ab3082c4bebd306c/svg"
                              srcset="" data-tid="b35288c1"><img aria-label="presentation" class="styles_icon__IXP4s"
                              src="https://avatars.mds.yandex.net/get-bunker/61205/c7ca1a7300068a2cf01c57a1f351ba9d89c20ee3/svg"
                              srcset="" data-tid="b35288c1">Установить на ТВ</a>
                        </nav>
                      </div>
                      <div class="styles_searchFormContainer__GyAL5">
                        <div class="styles_searchForm__AIMFU styles_searchFormDefault__QNml_" data-tid="8d7d7cbd">
                          <form class="styles_form__i86wS" action="/index.php">
                            <div class="styles_root__dTeXi styles_searchInputElement__qNbS4" data-tid="b0e8f9b">
                              <input type="text" name="kp_query"
                                class="styles_input__4vNAb kinopoisk-header-search-form-input__input" autocomplete="off"
                                aria-label="Фильмы, сериалы, персоны" placeholder="Фильмы, сериалы, персоны" value="" required>
                              <div class="styles_controlContainer__5VetH kinopoisk-header-search-form-input__control-container">
                                <a href="/s/" class="styles_advancedSearch__uwvnd" aria-label="advanced-search" tabindex="-1">
                                  <svg class="styles_advancedSearchIcon__Zxjax" xmlns="http://www.w3.org/2000/svg" width="18"
                                    height="18" viewbox="0 0 18 18">
                                    <path fill="#000" fill-rule="evenodd"
                                      d="M5.995 10.3A2.7 2.7 0 0 1 8.504 12H17v2H8.504a2.7 2.7 0 0 1-5.018 0H1v-2h2.486a2.7 2.7 0 0 1 2.509-1.7zm0 1.7a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm5.997-8.7A2.7 2.7 0 0 1 14.5 5H17v2h-2.5a2.7 2.7 0 0 1-5.017 0H1V5h8.483a2.7 2.7 0 0 1 2.509-1.7zm0 1.7a1 1 0 1 0 0 2 1 1 0 0 0 0-2z">
                                    </path>
                                  </svg></a><button type="submit" class="styles_root__CUh_v styles_submit__2AIpj"
                                  aria-label="submit" tabindex="-1" data-tid="f49ca51f">
                                  <svg class="styles_icon__1bYKL search-form-submit-button__icon"
                                    xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewbox="0 0 18 18">
                                    <path fill="#000" fill-rule="evenodd"
                                      d="M12.026 10.626L16 14.6 14.6 16l-3.974-3.974a5.5 5.5 0 1 1 1.4-1.4zM7.5 11.1a3.6 3.6 0 1 0 0-7.2 3.6 3.6 0 0 0 0 7.2z">
                                    </path>
                                  </svg>Найти</button>
                              </div>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                    <div class="styles_userContainer__hLiRQ">
                      <button type="button" class="styles_searchButton__kXYs6"><span
                          class="styles_searchButtonIconSmall__poB4V"></span></button>
                      <div class="styles_root__JgDCj" data-tid="1a82203d">
                        <div>
                          <a class="styles_plusDesktopBrandingButton__X4z6T styles_root__OkfMY styles_padding4x14__J5WqH styles_root__lbhjd styles_rootLight__4o4CJ styles_rootPlus__Xx9Yy"
                            href="https://hd.kinopoisk.ru/?source=kinopoisk_head_button">Попробовать Плюс</a>
                        </div><button type="button" class="styles_loginButton__LWZQp">Войти</button>
                      </div>
                    </div>
                  </div>
                </header>
              </div>
              <div class="styles_middleContainer__vJjLN styles_baseContainer__bGRTT">
                <div class="styles_pending__AMNhR styles_height250__Dw0ef" data-tid="a913e1e7">
                  <div class="styles_themeTopBanner__RLKkf" data-tid="e810e001">
                    <div id="foxaddesktop_top_banner" class="styles_container__XXCpX" data-tid="9501d3f4"></div>
                    <script
                      nonce="bhDiQUN2GJmKB+KGaLu11w==">try { window.adfoxService.renderBanner('foxaddesktop_top_banner', JSON.parse('{"containerId":"foxaddesktop_top_banner","ownerId":251518,"params":{"p1":"bvqwz","p2":"fkyf","puid10":"0","puid11":"0"},"cspNonce":"bhDiQUN2GJmKB+KGaLu11w=="}')); } catch (e) { console.error(e) }</script>
                  </div>
                </div>
                <div class="styles_mainContainer__59YLl">
                  <div class="styles_topbarSlot__aXY_D">
                    <div class="styles_root__nE_ni" data-tid="c0b0f220">
                      <div class="styles_breadcrumbs__p7eHZ">
                        <a href="/lists/categories/movies/" class="styles_link__5qIUj" data-tid="d4e8d214">Все списки</a>
                      </div>
                      <ul class="styles_root__OQKwo" data-tid="3888ab0a">
                        <li class="styles_linkContainer__bU_1e" data-tid="a7248f6"><a href="/lists/movies/top250/"
                            class="styles_link__dna_9">Топ 250 фильмов </a></li>
                        <li class="styles_linkContainer__bU_1e" data-tid="a7248f6"><a href="/lists/movies/series-top250/"
                            class="styles_link__dna_9">Топ 250 сериалов</a></li>
                        <li class="styles_linkContainer__bU_1e" data-tid="a7248f6"><a href="/lists/categories/movies/2/"
                            class="styles_link__dna_9">Онлайн-кинотеатр</a></li>
                        <li class="styles_linkContainer__bU_1e" data-tid="a7248f6"><a href="/top/navigator/"
                            class="styles_link__dna_9">Навигатор</a></li>
                      </ul>
                    </div>
                  </div>
                  <div class="styles_sidebarSlot__7_4eF">
                    <div>
                      <div class="styles_sticky__pWqAL">
                        <aside data-tid="34629c2">
                          <ul class="styles_list__41Dj6 styles_wrap__Y_qDN" data-tid="c013358a">
                            <a href="/lists/movies/top250/?b=films" class="styles_root__omMgy" aria-disabled="false"
                              data-tid="d4e8d214">Фильмы</a><a href="/lists/movies/top250/?b=series"
                              class="styles_root__omMgy styles_disabled__VYDrH" aria-disabled="true"
                              data-tid="d4e8d214">Сериалы</a><a href="/lists/movies/top250/?b=high_rated"
                              class="styles_root__omMgy" aria-disabled="false" data-tid="d4e8d214">С высоким рейтингом</a><a
                              href="/lists/movies/top250/?b=russian" class="styles_root__omMgy" aria-disabled="false"
                              data-tid="d4e8d214">Российские</a><a href="/lists/movies/top250/?b=foreign"
                              class="styles_root__omMgy" aria-disabled="false" data-tid="d4e8d214">Зарубежные</a><a
                              href="/lists/movies/top250/?b=released" class="styles_root__omMgy" aria-disabled="false"
                              data-tid="d4e8d214">Вышедшие</a><a href="/lists/movies/top250/?b=exclude_viewed"
                              class="styles_root__omMgy styles_disabled__VYDrH" aria-disabled="true" data-tid="d4e8d214">Скрыть
                              просмотренные</a>
                          </ul>
                          <details class="styles_root__POHZU styles_bordered__Y3kj6" open data-tid="ba5b39a6">
                            <summary class="styles_heading__djRMl">Страны</summary>
                            <div class="styles_body__R3Nqc">
                              <div class="styles_select__5bQ7Z styles_root__UeIta" data-tid="d773c274">
                                <div class="styles_selectButton__4xHt7 styles_button__3dBmr styles_thin__zGEcp">
                                  <span class="styles_buttonCaption__woeen styles_flex__wStQl">Все страны</span>
                                </div>
                              </div>
                            </div>
                          </details>
                          <details class="styles_root__POHZU styles_bordered__Y3kj6" open data-tid="ba5b39a6">
                            <summary class="styles_heading__djRMl">Жанры</summary>
                            <div class="styles_body__R3Nqc">
                              <div class="styles_select__5bQ7Z styles_root__UeIta" data-tid="d773c274">
                                <div class="styles_selectButton__4xHt7 styles_button__3dBmr styles_thin__zGEcp">
                                  <span class="styles_buttonCaption__woeen styles_flex__wStQl">Все жанры</span>
                                </div>
                              </div>
                            </div>
                          </details>
                          <details class="styles_root__POHZU styles_bordered__Y3kj6" open data-tid="ba5b39a6">
                            <summary class="styles_heading__djRMl">Годы</summary>
                            <div class="styles_body__R3Nqc">
                              <div class="styles_select__5bQ7Z styles_root__UeIta" data-tid="d773c274">
                                <div class="styles_selectButton__4xHt7 styles_button__3dBmr styles_thin__zGEcp">
                                  <span class="styles_buttonCaption__woeen styles_flex__wStQl">Все годы</span>
                                </div>
                              </div>
                            </div>
                          </details>
                        </aside>
                      </div>
                    </div>
                  </div>
                  <div class="styles_contentSlot__h_lSN">
                    <span id="selections_content_start_anchor"></span>
                    <main>
                      <div class="styles_root__KIenM" data-tid="bb6db913">
                        <div class="styles_textWrapper___VeYU">
                          <h1 class="styles_title__jB8AZ">250 лучших фильмов</h1>
                          <p class="styles_description__FEk94">Рейтинг составлен по результатам голосования посетителей сайта.
                            Любой желающий может принять в нем участие, проголосовав за свой любимый фильм.</p>
                        </div>
                        <div class="styles_root__OWj40 styles_listViewVariant__4VOSL" data-tid="3e43fc06">
                          <img class="styles_cover__7Uizq"
                            src="//avatars.mds.yandex.net/get-bunker/56833/3ee361778f24483f04a6819bc6d84bcfba9030e4/384x384"
                            alt="">
                        </div>
                      </div>
                      <div class="styles_root__aRjzv" data-tid="c97bf23b">
                        <a href="/lists/movies/top250/?ss_subscription=ANY" class="styles_root__o_aAP" data-tid="d4e8d214"><span
                            class="styles_title__skJ4z">Онлайн</span><span
                            class="styles_subtitle__V93vt">24&nbsp;фильма</span></a><a href="/lists/movies/top250/"
                          class="styles_root__o_aAP styles_rootActive__xFaoQ" data-tid="d4e8d214"><span
                            class="styles_title__skJ4z">Все</span><span
                            class="styles_subtitle__V93vt">250&nbsp;фильмов</span></a>
                        <div class="styles_rightSlot__Fqmxo">
                          <div class="styles_root__zUQ5a" data-tid="57a69acb">
                            <button type="button" class="styles_dropdownButton__3zGAH"><span
                                class="styles_selectedItem__KM2xi">По порядку</span><span
                                class="styles_arrowIcon__Szl2w"></span></button>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">1</span>
                          </div>
                          <a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X" data-tid="23a2a59">
                            <img class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Зеленая миля"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/4057c4b8-8208-4a04-b169-26b0661453e3/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/4057c4b8-8208-4a04-b169-26b0661453e3/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/4057c4b8-8208-4a04-b169-26b0661453e3/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              9.1
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/435/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Зеленая миля</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">The Green
                                    Mile, 1999, 189&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • драма&nbsp;&nbsp;Режиссёр: Фрэнк
                                      Дарабонт</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Том Хэнкс, Дэвид
                                      Морс</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">9.1</span><span
                                      class="styles_kinopoiskCount__2_VPQ">14 657</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->1
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">2</span>
                          </div>
                          <a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X" data-tid="23a2a59">
                            <img class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Побег из Шоушенка"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/0b76b2a2-d1c7-4f04-a284-80ff7bb709a4/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/0b76b2a2-d1c7-4f04-a284-80ff7bb709a4/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/0b76b2a2-d1c7-4f04-a284-80ff7bb709a4/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              9.1
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/326/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Побег из Шоушенка</span>
                                  </div>
                                  <span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">
                                    The Shawshank Redemption, 1994, 142&nbsp;мин.
                                  </span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">
                                      США • драма&nbsp;&nbsp;Режиссёр: Фрэнк Дарабонт
                                    </span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Тим Роббинс, Морган
                                      Фриман</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.9</span><span
                                      class="styles_kinopoiskCount__2_VPQ">11 380</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->2
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">3</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Список Шиндлера"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/b327ada7-d790-49ae-8b24-374497a0980c/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/b327ada7-d790-49ae-8b24-374497a0980c/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1773646/b327ada7-d790-49ae-8b24-374497a0980c/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.8
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/329/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Список Шиндлера</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus"
                                    data-tid="31fba632">Schindler's List, 1993, 195&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • драма&nbsp;&nbsp;Режиссёр:
                                      Стивен Спилберг</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Лиам Нисон, Бен
                                      Кингсли</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.9</span><span
                                      class="styles_kinopoiskCount__2_VPQ">10 239</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->3
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/329/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 299 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">4</span>
                          </div>
                          <a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X" data-tid="23a2a59">
                            <img class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Властелин колец: Возвращение короля"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/4303601/e410c71f-baa1-4fe5-bb29-aedb4662f49b/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/4303601/e410c71f-baa1-4fe5-bb29-aedb4662f49b/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/4303601/e410c71f-baa1-4fe5-bb29-aedb4662f49b/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/3498/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Властелин колец: Возвращение короля</span>
                                  </div>
                                  <span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">The Lord of the
                                    Rings: The Return of the King, 2003, 201&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-35.gif" alt="Новая Зеландия"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Новая Зеландия •
                                      фэнтези&nbsp;&nbsp;Режиссёр: Питер Джексон</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Элайджа Вуд, Вигго
                                      Мортенсен</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.9</span><span
                                      class="styles_kinopoiskCount__2_VPQ">12 496</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->4
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">5</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Форрест Гамп"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/3560b757-9b95-45ec-af8c-623972370f9d/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/3560b757-9b95-45ec-af8c-623972370f9d/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/3560b757-9b95-45ec-af8c-623972370f9d/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.9
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/448/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Форрест Гамп</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Forrest
                                    Gump, 1994, 142&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • драма&nbsp;&nbsp;Режиссёр:
                                      Роберт Земекис</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Том Хэнкс, Робин
                                      Райт</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.8</span><span
                                      class="styles_kinopoiskCount__2_VPQ">15 420</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->5
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/448/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 149 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">6</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Властелин колец: Две крепости"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/5f05fb11-3a26-4ccc-bc6e-8f3707fc72d8/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/5f05fb11-3a26-4ccc-bc6e-8f3707fc72d8/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1704946/5f05fb11-3a26-4ccc-bc6e-8f3707fc72d8/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/312/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Властелин колец: Две крепости</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">The Lord
                                    of the Rings: The Two Towers, 2002, 179&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-35.gif" alt="Новая Зеландия"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Новая Зеландия •
                                      фэнтези&nbsp;&nbsp;Режиссёр: Питер Джексон</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Элайджа Вуд, Иэн
                                      Маккеллен</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.8</span><span
                                      class="styles_kinopoiskCount__2_VPQ">13 579</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->6
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">7</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Властелин колец: Братство Кольца" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/6201401/a2d5bcae-a1a9-442f-8195-f5373a5ba77f/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/6201401/a2d5bcae-a1a9-442f-8195-f5373a5ba77f/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/6201401/a2d5bcae-a1a9-442f-8195-f5373a5ba77f/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/328/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Властелин колец: Братство Кольца</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">The Lord
                                    of the Rings: The Fellowship of the Ring, 2001, 178&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-35.gif" alt="Новая Зеландия"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Новая Зеландия •
                                      фэнтези&nbsp;&nbsp;Режиссёр: Питер Джексон</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Элайджа Вуд, Иэн
                                      Маккеллен</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.8</span><span
                                      class="styles_kinopoiskCount__2_VPQ">14 479</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->7
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">8</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="1+1"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/bf93b465-1189-4155-9dd1-cb9fb5cb1bb5/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/bf93b465-1189-4155-9dd1-cb9fb5cb1bb5/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1946459/bf93b465-1189-4155-9dd1-cb9fb5cb1bb5/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.8
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/535341/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">1+1</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus"
                                    data-tid="31fba632">Intouchables, 2011, 112&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-8.gif" alt="Франция"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Франция • драма&nbsp;&nbsp;Режиссёр:
                                      Оливье Накаш</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Франсуа Клюзе, Омар
                                      Си</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.8</span><span
                                      class="styles_kinopoiskCount__2_VPQ">14 606</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->8
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">9</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Криминальное чтиво" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1900788/87b5659d-a159-4224-9bff-d5a5d109a53b/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1900788/87b5659d-a159-4224-9bff-d5a5d109a53b/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1900788/87b5659d-a159-4224-9bff-d5a5d109a53b/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/342/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Криминальное чтиво</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Pulp
                                    Fiction, 1994, 154&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • криминал&nbsp;&nbsp;Режиссёр:
                                      Квентин Тарантино</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Джон Траволта, Сэмюэл Л.
                                      Джексон</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.7</span><span
                                      class="styles_kinopoiskCount__2_VPQ">14 888</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->9
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/342/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 149 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">10</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Иван Васильевич меняет профессию" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/6201401/a7ef44b8-1983-4992-a889-da6f87a3f559/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/6201401/a7ef44b8-1983-4992-a889-da6f87a3f559/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/6201401/a7ef44b8-1983-4992-a889-da6f87a3f559/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.8
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/42664/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Иван Васильевич меняет профессию</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">1973,
                                    88&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-13.gif" alt="СССР"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">СССР • комедия&nbsp;&nbsp;Режиссёр:
                                      Леонид Гайдай</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Александр Демьяненко,
                                      Юрий Яковлев</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.7</span><span
                                      class="styles_kinopoiskCount__2_VPQ">9 823</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->10
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_listSuperBanner__rro4V styles_minHeight250__U6rQ8" style="min-width:1px"
                          data-tid="517927c6"></div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">11</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Король Лев"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/60aa1abc-b754-4817-ad9c-0bcda427a12b/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/60aa1abc-b754-4817-ad9c-0bcda427a12b/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1704946/60aa1abc-b754-4817-ad9c-0bcda427a12b/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.8
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/2360/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Король Лев</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">The Lion
                                    King, 1994, 88&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • мультфильм&nbsp;&nbsp;Режиссёр:
                                      Роджер Аллерс</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Мэттью Бродерик, Джереми
                                      Айронс</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.7</span><span
                                      class="styles_kinopoiskCount__2_VPQ">11 041</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->11
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/2360/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 129 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">12</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Интерстеллар" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1600647/430042eb-ee69-4818-aed0-a312400a26bf/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1600647/430042eb-ee69-4818-aed0-a312400a26bf/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1600647/430042eb-ee69-4818-aed0-a312400a26bf/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/258687/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Интерстеллар</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus"
                                    data-tid="31fba632">Interstellar, 2014, 169&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-11.gif" alt="Великобритания"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-6.gif" alt="Канада"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Великобритания •
                                      фантастика&nbsp;&nbsp;Режиссёр: Кристофер Нолан</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Мэттью МакКонахи, Энн
                                      Хэтэуэй</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.7</span><span
                                      class="styles_kinopoiskCount__2_VPQ">14 225</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->12
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">13</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Тайна Коко"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/6e11a16e-c9e7-491f-9162-01098a7d8dd9/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/6e11a16e-c9e7-491f-9162-01098a7d8dd9/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1946459/6e11a16e-c9e7-491f-9162-01098a7d8dd9/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/679486/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Тайна Коко</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Coco,
                                    2017, 105&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-17.gif" alt="Мексика"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • мультфильм&nbsp;&nbsp;Режиссёр:
                                      Ли Анкрич</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Энтони Гонсалес, Гаэль
                                      Гарсиа Берналь</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.7</span><span
                                      class="styles_kinopoiskCount__2_VPQ">15 755</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->13
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/679486/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 129 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">14</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Назад в будущее" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/73cf2ed0-fd52-47a2-9e26-74104360786a/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/73cf2ed0-fd52-47a2-9e26-74104360786a/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/73cf2ed0-fd52-47a2-9e26-74104360786a/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/476/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Назад в будущее</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Back to
                                    the Future, 1985, 116&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • фантастика&nbsp;&nbsp;Режиссёр:
                                      Роберт Земекис</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Майкл Дж. Фокс, Кристофер
                                      Ллойд</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.7</span><span
                                      class="styles_kinopoiskCount__2_VPQ">13 251</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->14
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/476/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 299 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">15</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="ВАЛЛ·И"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/146b1b20-347b-4b6a-98c8-fdc2c75495cb/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/146b1b20-347b-4b6a-98c8-fdc2c75495cb/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1946459/146b1b20-347b-4b6a-98c8-fdc2c75495cb/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.3
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/279102/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">ВАЛЛ·И</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">WALL·E,
                                    2008, 98&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • мультфильм&nbsp;&nbsp;Режиссёр:
                                      Эндрю Стэнтон</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Бен Бертт, Элисса
                                      Найт</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">14 999</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->15
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/279102/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 129 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">16</span><span
                              class="styles_diff__vjF_8 styles_positive__JjZvH">1</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Темный рыцарь" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/0fa5bf50-d5ad-446f-a599-b26d070c8b99/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/0fa5bf50-d5ad-446f-a599-b26d070c8b99/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/0fa5bf50-d5ad-446f-a599-b26d070c8b99/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.5
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/111543/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Темный рыцарь</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">The Dark
                                    Knight, 2008, 152&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-11.gif" alt="Великобритания"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • фантастика&nbsp;&nbsp;Режиссёр:
                                      Кристофер Нолан</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Кристиан Бэйл, Хит
                                      Леджер</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">18 283</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->16
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">17</span><span
                              class="styles_diff__vjF_8 styles_negative__mgLxq">1</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Начало"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1629390/8ab9a119-dd74-44f0-baec-0629797483d7/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1629390/8ab9a119-dd74-44f0-baec-0629797483d7/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1629390/8ab9a119-dd74-44f0-baec-0629797483d7/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.7
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/447301/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Начало</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Inception,
                                    2010, 148&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-11.gif" alt="Великобритания"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • фантастика&nbsp;&nbsp;Режиссёр:
                                      Кристофер Нолан</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Леонардо ДиКаприо, Джозеф
                                      Гордон-Левитт</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">13 918</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->17
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">18</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Бойцовский клуб" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1898899/8ef070c9-2570-4540-9b83-d7ce759c0781/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1898899/8ef070c9-2570-4540-9b83-d7ce759c0781/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1898899/8ef070c9-2570-4540-9b83-d7ce759c0781/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/361/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Бойцовский клуб</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Fight
                                    Club, 1999, 139&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-3.gif" alt="Германия"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • триллер&nbsp;&nbsp;Режиссёр:
                                      Дэвид Финчер</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Эдвард Нортон, Брэд
                                      Питт</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">12 539</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->18
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/361/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 199 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">19</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Унесённые призраками" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/6201401/ae3b699c-3db7-4196-a869-39b610bfe706/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/6201401/ae3b699c-3db7-4196-a869-39b610bfe706/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/6201401/ae3b699c-3db7-4196-a869-39b610bfe706/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.5
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/370/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Унесённые призраками</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Sen to
                                    Chihiro no kamikakushi, 2001, 125&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-9.gif" alt="Япония"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Япония • аниме&nbsp;&nbsp;Режиссёр:
                                      Хаяо Миядзаки</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Руми Хиираги, Мию
                                      Ирино</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">11 224</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->19
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/370/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 99 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">20</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Карты, деньги, два ствола" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/6492653f-11d5-4087-a1d2-ec7a74f161d3/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/6492653f-11d5-4087-a1d2-ec7a74f161d3/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/6492653f-11d5-4087-a1d2-ec7a74f161d3/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/522/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Карты, деньги, два ствола</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Lock,
                                    Stock and Two Smoking Barrels, 1998, 107&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-11.gif" alt="Великобритания"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Великобритания •
                                      боевик&nbsp;&nbsp;Режиссёр: Гай Ричи</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Джейсон Флеминг, Декстер
                                      Флетчер</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">13 033</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->20
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/522/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 119 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">21</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Москва слезам не верит" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/3f1fcc8f-733b-4d0c-8746-7c5e12e2bebf/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/3f1fcc8f-733b-4d0c-8746-7c5e12e2bebf/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/3f1fcc8f-733b-4d0c-8746-7c5e12e2bebf/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.4
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/46708/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Москва слезам не верит</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">1979,
                                    150&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-13.gif" alt="СССР"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">СССР • мелодрама&nbsp;&nbsp;Режиссёр:
                                      Владимир Меньшов</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Вера Алентова, Ирина
                                      Муравьёва</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">9 893</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->21
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">22</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Гладиатор"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/7c3460dc-344d-433f-8220-f18d86c8397d/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/7c3460dc-344d-433f-8220-f18d86c8397d/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/7c3460dc-344d-433f-8220-f18d86c8397d/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/474/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Гладиатор</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Gladiator,
                                    2000, 155&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-11.gif" alt="Великобритания"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-111.gif" alt="Мальта"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • история&nbsp;&nbsp;Режиссёр:
                                      Ридли Скотт</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Рассел Кроу, Хоакин
                                      Феникс</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">12 820</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->22
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/474/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 299 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">23</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Джентльмены удачи" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/218102a7-96be-4d7e-8029-815de0f37cfa/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/218102a7-96be-4d7e-8029-815de0f37cfa/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1946459/218102a7-96be-4d7e-8029-815de0f37cfa/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.5
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/44386/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Джентльмены удачи</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">1971,
                                    84&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-13.gif" alt="СССР"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">СССР • комедия&nbsp;&nbsp;Режиссёр:
                                      Александр Серый</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Евгений Леонов, Георгий
                                      Вицин</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">7 793</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->23
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">24</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Приключения Шерлока Холмса и доктора Ватсона: Собака Баскервилей" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/f08ba921-02dd-4d19-9c15-757e040335d6/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/f08ba921-02dd-4d19-9c15-757e040335d6/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/f08ba921-02dd-4d19-9c15-757e040335d6/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/77263/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Приключения Шерлока Холмса и доктора Ватсона: Собака
                                      Баскервилей</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">1981,
                                    154&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-13.gif" alt="СССР"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">СССР • детектив&nbsp;&nbsp;Режиссёр:
                                      Игорь Масленников</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Василий Ливанов, Виталий
                                      Соломин</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">4 669</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->24
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">25</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Достучаться до небес" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/aac37c55-3aa2-4f4d-b3ed-9f59ba426f92/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/aac37c55-3aa2-4f4d-b3ed-9f59ba426f92/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1773646/aac37c55-3aa2-4f4d-b3ed-9f59ba426f92/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/32898/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Достучаться до небес</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Knockin'
                                    on Heaven's Door, 1997, 87&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-3.gif" alt="Германия"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Германия • драма&nbsp;&nbsp;Режиссёр:
                                      Томас Ян</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Тиль Швайгер, Ян Йозеф
                                      Лиферс</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">10 590</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->25
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">26</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Матрица"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/eed1de3a-5400-43b3-839e-22490389bf54/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/eed1de3a-5400-43b3-839e-22490389bf54/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1704946/eed1de3a-5400-43b3-839e-22490389bf54/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.5
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/301/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Матрица</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">The
                                    Matrix, 1999, 136&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-25.gif" alt="Австралия"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • фантастика&nbsp;&nbsp;Режиссёр:
                                      Лана Вачовски</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Киану Ривз, Лоренс
                                      Фишбёрн</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">19 759</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->26
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">27</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Шрэк"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/7ade06a8-4178-4386-9ee2-87fec5a172eb/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/7ade06a8-4178-4386-9ee2-87fec5a172eb/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1946459/7ade06a8-4178-4386-9ee2-87fec5a172eb/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.1
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/430/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Шрэк</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Shrek,
                                    2001, 90&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-9.gif" alt="Япония"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • мультфильм&nbsp;&nbsp;Режиссёр:
                                      Эндрю Адамсон</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Майк Майерс, Эдди
                                      Мёрфи</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.6</span><span
                                      class="styles_kinopoiskCount__2_VPQ">18 071</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->27
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/430/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 299 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">28</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="...А зори здесь тихие" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1629390/7d011306-5cf5-421d-b01d-3684e500be92/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1629390/7d011306-5cf5-421d-b01d-3684e500be92/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1629390/7d011306-5cf5-421d-b01d-3684e500be92/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.5
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/43395/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">...А зори здесь тихие</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">1972,
                                    160&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-13.gif" alt="СССР"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">СССР • драма&nbsp;&nbsp;Режиссёр:
                                      Станислав Ростоцкий</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Елена Драпеко, Екатерина
                                      Маркова</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">3 668</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->28
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">29</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Клаус"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/279f9f19-6a29-4fae-935e-54ddf80c653b/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/279f9f19-6a29-4fae-935e-54ddf80c653b/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1773646/279f9f19-6a29-4fae-935e-54ddf80c653b/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/957887/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Клаус</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Klaus,
                                    2019, 96&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-15.gif" alt="Испания"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-11.gif" alt="Великобритания"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Испания •
                                      мультфильм&nbsp;&nbsp;Режиссёр: Серхио Паблос</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Джейсон Шварцман, Дж.К.
                                      Симмонс</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">9 482</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->29
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">30</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Брат"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/e9008e2f-433f-43b0-b9b8-2ea8e3fb6c9b/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/e9008e2f-433f-43b0-b9b8-2ea8e3fb6c9b/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1704946/e9008e2f-433f-43b0-b9b8-2ea8e3fb6c9b/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.3
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/41519/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Брат</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">1997,
                                    100&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-2.gif" alt="Россия"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Россия • драма&nbsp;&nbsp;Режиссёр:
                                      Алексей Балабанов</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Сергей Бодров мл., Виктор
                                      Сухоруков</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">20 448</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->30
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">31</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Пираты Карибского моря: Проклятие Черной жемчужины" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/d7e3dbd6-e4a9-4485-b751-d02f49825166/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/d7e3dbd6-e4a9-4485-b751-d02f49825166/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1773646/d7e3dbd6-e4a9-4485-b751-d02f49825166/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.3
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/4374/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Пираты Карибского моря: Проклятие Черной жемчужины</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Pirates of
                                    the Caribbean: The Curse of the Black Pearl, 2003, 143&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • фэнтези&nbsp;&nbsp;Режиссёр: Гор
                                      Вербински</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Джонни Депп, Джеффри
                                      Раш</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">17 249</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->31
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/4374/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 129 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">32</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Остров проклятых" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/4303601/617303b7-cfa7-4273-bd1d-63974bf68927/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/4303601/617303b7-cfa7-4273-bd1d-63974bf68927/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/4303601/617303b7-cfa7-4273-bd1d-63974bf68927/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.5
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/397667/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Остров проклятых</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Shutter
                                    Island, 2009, 138&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • триллер&nbsp;&nbsp;Режиссёр:
                                      Мартин Скорсезе</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Леонардо ДиКаприо, Марк
                                      Руффало</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">18 309</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->32
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/397667/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 199 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">33</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Большой куш" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1777765/2d213f77-3c09-45ac-9d97-8c479d18627b/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1777765/2d213f77-3c09-45ac-9d97-8c479d18627b/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1777765/2d213f77-3c09-45ac-9d97-8c479d18627b/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.5
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/526/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Большой куш</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Snatch,
                                    2000, 104&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-11.gif" alt="Великобритания"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Великобритания •
                                      криминал&nbsp;&nbsp;Режиссёр: Гай Ричи</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Джейсон Стэйтем, Стивен
                                      Грэм</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">9 898</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->33
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/526/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 199 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">34</span><span
                              class="styles_diff__vjF_8 styles_positive__JjZvH">1</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Поймай меня, если сможешь" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/e38dd6f9-610e-4c90-8540-dc48560cb9cc/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/e38dd6f9-610e-4c90-8540-dc48560cb9cc/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1704946/e38dd6f9-610e-4c90-8540-dc48560cb9cc/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.5
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/324/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Поймай меня, если сможешь</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Catch Me
                                    If You Can, 2002, 141&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-6.gif" alt="Канада"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • криминал&nbsp;&nbsp;Режиссёр:
                                      Стивен Спилберг</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Леонардо ДиКаприо, Том
                                      Хэнкс</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">18 458</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->34
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">35</span><span
                              class="styles_diff__vjF_8 styles_negative__mgLxq">1</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Джентльмены" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/637271d5-61b4-4e46-ac83-6d07494c7645/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/637271d5-61b4-4e46-ac83-6d07494c7645/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/637271d5-61b4-4e46-ac83-6d07494c7645/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.5
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/1143242/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Джентльмены</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">The
                                    Gentlemen, 2019, 113&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-11.gif" alt="Великобритания"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Великобритания •
                                      криминал&nbsp;&nbsp;Режиссёр: Гай Ричи</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Мэттью МакКонахи, Чарли
                                      Ханнэм</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">28 608</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->35
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">36</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="В бой идут одни «старики»" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/03265560-cacb-429a-8ccd-50403f2db552/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/03265560-cacb-429a-8ccd-50403f2db552/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1704946/03265560-cacb-429a-8ccd-50403f2db552/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.7
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/25108/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj" data-tid="4502216a">В
                                      бой идут одни «старики»</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">1973,
                                    87&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-13.gif" alt="СССР"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">СССР • военный&nbsp;&nbsp;Режиссёр:
                                      Леонид Быков</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Леонид Быков, Сергей
                                      Подгорный</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">3 492</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->36
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">37</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Операция «Ы» и другие приключения Шурика" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/1fb487b9-744f-4d2e-8f89-3e1049acc5b0/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/1fb487b9-744f-4d2e-8f89-3e1049acc5b0/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1946459/1fb487b9-744f-4d2e-8f89-3e1049acc5b0/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.7
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/42782/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Операция «Ы» и другие приключения Шурика</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">1965,
                                    95&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-13.gif" alt="СССР"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">СССР • комедия&nbsp;&nbsp;Режиссёр:
                                      Леонид Гайдай</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Александр Демьяненко,
                                      Наталья Селезнёва</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">9 758</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->37
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">38</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Гарри Поттер и узник Азкабана" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/4303601/3eabac99-fb98-4b12-ba9f-6172782d54c6/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/4303601/3eabac99-fb98-4b12-ba9f-6172782d54c6/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/4303601/3eabac99-fb98-4b12-ba9f-6172782d54c6/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.2
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/322/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Гарри Поттер и узник Азкабана</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Harry
                                    Potter and the Prisoner of Azkaban, 2004, 142&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-11.gif" alt="Великобритания"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Великобритания •
                                      фэнтези&nbsp;&nbsp;Режиссёр: Альфонсо Куарон</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Дэниэл Рэдклифф, Руперт
                                      Гринт</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">15 500</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->38
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">39</span><span
                              class="styles_diff__vjF_8 styles_positive__JjZvH">1</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Крестный отец" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/c11652e8-653b-47c1-8e72-1552399a775b/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/c11652e8-653b-47c1-8e72-1552399a775b/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/c11652e8-653b-47c1-8e72-1552399a775b/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.7
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/325/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Крестный отец</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">The
                                    Godfather, 1972, 175&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • драма&nbsp;&nbsp;Режиссёр:
                                      Фрэнсис Форд Коппола</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Марлон Брандо, Аль
                                      Пачино</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">7 753</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->39
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/325/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 169 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">40</span><span
                              class="styles_diff__vjF_8 styles_negative__mgLxq">1</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Остров сокровищ" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1600647/8059636c-5d02-4e74-94e6-910a14871d62/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1600647/8059636c-5d02-4e74-94e6-910a14871d62/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1600647/8059636c-5d02-4e74-94e6-910a14871d62/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.2
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/573759/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Остров сокровищ</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">1988,
                                    107&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-13.gif" alt="СССР"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">СССР • мультфильм&nbsp;&nbsp;Режиссёр:
                                      Давид Черкасский</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Владимир Заднепровский,
                                      Евгений Паперный</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">7 501</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->40
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">41</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="По соображениям совести" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/83a758fc-d3f9-485a-b75f-530fb611d63c/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/83a758fc-d3f9-485a-b75f-530fb611d63c/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1946459/83a758fc-d3f9-485a-b75f-530fb611d63c/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.1
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/649917/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj" data-tid="4502216a">По
                                      соображениям совести</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Hacksaw
                                    Ridge, 2016, 139&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-25.gif" alt="Австралия"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Австралия • драма&nbsp;&nbsp;Режиссёр:
                                      Мэл Гибсон</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Эндрю Гарфилд, Сэм
                                      Уортингтон</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">17 091</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->41
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/649917/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 129 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">42</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Брат 2"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/80eab631-346c-4c29-b14d-1fa1438158f9/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1704946/80eab631-346c-4c29-b14d-1fa1438158f9/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1704946/80eab631-346c-4c29-b14d-1fa1438158f9/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.1
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/41520/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Брат 2</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">2000,
                                    127&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-2.gif" alt="Россия"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Россия • боевик&nbsp;&nbsp;Режиссёр:
                                      Алексей Балабанов</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Сергей Бодров мл., Виктор
                                      Сухоруков</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">16 811</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->42
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">43</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Гарри Поттер и философский камень" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1898899/27ed5c19-a045-49dd-8624-5f629c5d96e0/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1898899/27ed5c19-a045-49dd-8624-5f629c5d96e0/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1898899/27ed5c19-a045-49dd-8624-5f629c5d96e0/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.2
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/689/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Гарри Поттер и философский камень</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Harry
                                    Potter and the Sorcerer's Stone, 2001, 152&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-11.gif" alt="Великобритания"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Великобритания •
                                      фэнтези&nbsp;&nbsp;Режиссёр: Крис Коламбус</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Дэниэл Рэдклифф, Руперт
                                      Гринт</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">14 846</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->43
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">44</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Они сражались за Родину" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/4716873/3c933437-f01a-4a21-abae-69369456aa9c/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/4716873/3c933437-f01a-4a21-abae-69369456aa9c/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/4716873/3c933437-f01a-4a21-abae-69369456aa9c/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.4
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/42770/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Они сражались за Родину</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">1975,
                                    160&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-13.gif" alt="СССР"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">СССР • драма&nbsp;&nbsp;Режиссёр:
                                      Сергей Бондарчук</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Василий Шукшин, Вячеслав
                                      Тихонов</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.5</span><span
                                      class="styles_kinopoiskCount__2_VPQ">2 816</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->44
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">45</span><span
                              class="styles_diff__vjF_8 styles_positive__JjZvH">2</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Жизнь прекрасна" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/97353be9-362d-47bc-bbeb-c366db381c33/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/97353be9-362d-47bc-bbeb-c366db381c33/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/97353be9-362d-47bc-bbeb-c366db381c33/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.6
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/381/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Жизнь прекрасна</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">La vita è
                                    bella, 1997, 116&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-14.gif" alt="Италия"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Италия • военный&nbsp;&nbsp;Режиссёр:
                                      Роберто Бениньи</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Роберто Бениньи,
                                      Николетта Браски</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.4</span><span
                                      class="styles_kinopoiskCount__2_VPQ">3 461</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->45
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">46</span><span
                              class="styles_diff__vjF_8 styles_negative__mgLxq">1</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Прислуга"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/40a83515-3eca-4cb7-ba31-351b69c9333f/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/40a83515-3eca-4cb7-ba31-351b69c9333f/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/40a83515-3eca-4cb7-ba31-351b69c9333f/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.2
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/470553/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Прислуга</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">The Help,
                                    2011, 146&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • драма&nbsp;&nbsp;Режиссёр: Тейт
                                      Тейлор</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Эмма Стоун, Виола
                                      Дэвис</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.4</span><span
                                      class="styles_kinopoiskCount__2_VPQ">10 431</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->46
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/470553/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 129 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">47</span><span
                              class="styles_diff__vjF_8 styles_negative__mgLxq">1</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Титаник"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/96d93e3a-fdbf-4b6f-b02d-2fc9c2648a18/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/96d93e3a-fdbf-4b6f-b02d-2fc9c2648a18/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1773646/96d93e3a-fdbf-4b6f-b02d-2fc9c2648a18/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.4
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/2213/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Титаник</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Titanic,
                                    1997, 194&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-17.gif" alt="Мексика"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-25.gif" alt="Австралия"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • мелодрама&nbsp;&nbsp;Режиссёр:
                                      Джеймс Кэмерон</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Леонардо ДиКаприо, Кейт
                                      Уинслет</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.4</span><span
                                      class="styles_kinopoiskCount__2_VPQ">14 431</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->47
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/2213/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 129 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">48</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Джанго освобожденный" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1600647/b7d77143-158c-4c30-bcd9-7b94182492b0/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1600647/b7d77143-158c-4c30-bcd9-7b94182492b0/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1600647/b7d77143-158c-4c30-bcd9-7b94182492b0/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.2
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/586397/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Джанго освобожденный</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Django
                                    Unchained, 2012, 165&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">США • вестерн&nbsp;&nbsp;Режиссёр:
                                      Квентин Тарантино</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Джейми Фокс, Кристоф
                                      Вальц</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.4</span><span
                                      class="styles_kinopoiskCount__2_VPQ">17 152</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->48
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">49</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd" alt="Леон"
                              loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/bd1dcbd5-ad6b-4094-8243-d4d506c8b2e1/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1773646/bd1dcbd5-ad6b-4094-8243-d4d506c8b2e1/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1773646/bd1dcbd5-ad6b-4094-8243-d4d506c8b2e1/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.7
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/389/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Леон</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Léon,
                                    1994, 133&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-8.gif" alt="Франция"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-1.gif" alt="США"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Франция • боевик&nbsp;&nbsp;Режиссёр:
                                      Люк Бессон</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Жан Рено, Натали
                                      Портман</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.4</span><span
                                      class="styles_kinopoiskCount__2_VPQ">9 715</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->49
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class=""></div>
                          </div>
                        </div>
                      </div>
                      <div>
                        <div class="styles_root__ti07r" data-tid="8a6cbb06">
                          <div class="styles_root__j_C_S" data-tid="24298726">
                            <span class="styles_position__TDe4E">50</span>
                          </div><a class="styles_poster__gJgwz styles_root__wgbNq styles_trailerButton__YTX4X"
                            data-tid="23a2a59"><img
                              class="styles_image__gRXvn styles_mediumSizeType__fPzdD image styles_root__DZigd"
                              alt="Ходячий замок" loading="lazy"
                              src="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/399bb0c1-2ac5-46ca-9efd-50d8708e2809/68x102"
                              srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1946459/399bb0c1-2ac5-46ca-9efd-50d8708e2809/68x102 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1946459/399bb0c1-2ac5-46ca-9efd-50d8708e2809/136x204 2x"
                              data-tid="d813cf42">
                            <div class="styles_rating__ni2L0 styles_root___s7Tg styles_rootMd__ZvdRj styles_rootPositive__PIwO2"
                              data-tid="d5cca7fd">
                              8.3
                            </div>
                          </a>
                          <div class="styles_content__nT2IG">
                            <div class="styles_upper__j8BIs">
                              <div class="styles_main__Y8zDm styles_mainWithNotCollapsedBeforeSlot__x4cWo">
                                <a href="/film/49684/" class="base-movie-main-info_link__YwtP1" data-tid="d4e8d214">
                                  <div class="base-movie-main-info_mainInfo__ZL_u3" data-tid="eb1cb4b8">
                                    <span class="styles_mainTitle__IFQyZ styles_activeMovieTittle__kJdJj"
                                      data-tid="4502216a">Ходячий замок</span>
                                  </div><span class="desktop-list-main-info_secondaryText__M_aus" data-tid="31fba632">Hauru no
                                    ugoku shiro, 2004, 119&nbsp;мин.</span>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <img src="https://st.kp.yandex.net/images/flags/flag-9.gif" alt="Япония"
                                      class="countries_flag__5cI2E" loading="lazy" data-tid="922aca86"><span
                                      class="desktop-list-main-info_truncatedText__IMQRP">Япония • аниме&nbsp;&nbsp;Режиссёр:
                                      Хаяо Миядзаки</span>
                                  </div>
                                  <div class="desktop-list-main-info_additionalInfo__Hqzof" data-tid="31fba632">
                                    <span class="desktop-list-main-info_truncatedText__IMQRP">В ролях: Тиэко Байсё, Такуя
                                      Кимура</span>
                                  </div>
                                </a>
                              </div>
                              <div class="styles_user__2wZvH">
                                <div class="styles_rating__LU3_x">
                                  <div class="styles_kinopoisk__JZttS" data-tid="98b770b">
                                    <span
                                      class="styles_kinopoiskValuePositive__vOb2E styles_kinopoiskValue__9qXjg">8.4</span><span
                                      class="styles_kinopoiskCount__2_VPQ">9 613</span>
                                  </div>
                                  <div class="styles_top250__KTSbT" data-tid="98b770b">
                                    Топ 250:
                                    <!-- -->50
                                  </div>
                                </div>
                                <div class="styles_root__Jhf3F" data-tid="5f28aaab">
                                  <button type="button" class="styles_plannedToWatch__DGwtf styles_button__ykcOG"
                                    data-tid="bcbcc29f"><span class="styles_icon__vllMt"></span>Буду смотреть</button>
                                  <div class="styles_root__rCGal styles_movieRatingVotingBar__Iuo2E" data-tid="4bac8177">
                                    <button type="button"
                                      class="styles_closedButton__jVYX8 styles_button__xjFTa styles_movieRatingButton__wKlKz"
                                      data-tid="5b23305b"><i class="styles_star__R6DQ_ styles_controls__X1G1t"></i></button>
                                  </div>
                                  <div class="styles_root__g0CT9" data-tid="4a36b453">
                                    <button type="button"
                                      class="styles_foldersButtons__FUXd0 styles_button__ggqHh styles_vertical__ujDx5"
                                      aria-label="Отметить" title="Отметить" data-tid="1a531fb8"></button>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="">
                              <div class="styles_root__ZH67U" data-tid="875b8a69">
                                <a class="styles_root__hwhAd styles_onlineButton__ER9Vt styles_inlineItem___co22 styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"
                                  href="/film/49684/watch/?from_block=kp-button-list"><span class="styles_icon__gSz3U"
                                    data-tid="63c3b8c4"></span>от 99 ₽</a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="styles_root__AT6_5 styles_root__RoFSb" data-tid="173d6058">
                        <a href="/lists/movies/top250/?page=1" class="styles_page__zbGy7 styles_active__fPiyK"
                          data-tid="d4e8d214">1</a><a href="/lists/movies/top250/?page=2" class="styles_page__zbGy7"
                          data-tid="d4e8d214">2</a><a href="/lists/movies/top250/?page=3" class="styles_page__zbGy7"
                          data-tid="d4e8d214">3</a><a href="/lists/movies/top250/?page=4" class="styles_page__zbGy7"
                          data-tid="d4e8d214">4</a><a href="/lists/movies/top250/?page=5" class="styles_page__zbGy7"
                          data-tid="d4e8d214">5</a><a href="/lists/movies/top250/?page=2"
                          class="styles_end__aEsmB styles_start__UvE6T" title="Вперед" data-tid="d4e8d214"></a>
                      </div>
                    </main>
                  </div>
                </div>
              </div>
              <div class="styles_footerContainer__UdQHs styles_baseContainer__bGRTT">
                <div>
                  <footer class="footer styles_rootDark__mtmaQ styles_root__sUtn2" data-tid="995d0553">
                    <section class="styles_root__kcCHj social-icons styles_socialMenu__gLaiH" data-tid="39df70f">
                      <a class="styles_icon__cHky_" href="https://vk.com/kinopoisk" target="_blank" rel="noopener noreferrer"
                        aria-label="presentation" data-tid="2e9b873"><img
                          src="//avatars.mds.yandex.net/get-bunker/118781/0ae3d1ca27d3794204beec7d3810025f8c2b7e87/svg"
                          alt="https://vk.com/kinopoisk" loading="lazy" data-tid="9107e4a2"></a><a class="styles_icon__cHky_"
                        href="https://www.facebook.com/kinopoisk" target="_blank" rel="noopener noreferrer"
                        aria-label="presentation" data-tid="2e9b873"><img
                          src="//avatars.mds.yandex.net/get-bunker/56833/0baf23635975a9f1b481833f37653aa2efceb3a1/svg"
                          alt="https://www.facebook.com/kinopoisk" loading="lazy" data-tid="9107e4a2"></a><a
                        class="styles_icon__cHky_" href="https://twitter.com/kinopoiskru" target="_blank"
                        rel="noopener noreferrer" aria-label="presentation" data-tid="2e9b873"><img
                          src="//avatars.mds.yandex.net/get-bunker/61205/97123f0bc0c689932a2fb6b62d3ab8ce04d7e936/svg"
                          alt="https://twitter.com/kinopoiskru" loading="lazy" data-tid="9107e4a2"></a><a
                        class="styles_icon__cHky_" href="https://telegram.me/kinopoisk" target="_blank"
                        rel="noopener noreferrer" aria-label="presentation" data-tid="2e9b873"><img
                          src="//avatars.mds.yandex.net/get-bunker/56833/9f570502e378d5e28a5a173a273fa811c4490a73/svg"
                          alt="https://telegram.me/kinopoisk" loading="lazy" data-tid="9107e4a2"></a><a
                        class="styles_icon__cHky_" href="https://www.instagram.com/kinopoisk/" target="_blank"
                        rel="noopener noreferrer" aria-label="presentation" data-tid="2e9b873"><img
                          src="//avatars.mds.yandex.net/get-bunker/50064/c6b1a28b4bf580d4cf96ec7f262aace67a4dde2e/svg"
                          alt="https://www.instagram.com/kinopoisk/" loading="lazy" data-tid="9107e4a2"></a><a
                        class="styles_icon__cHky_" href="https://www.youtube.com/user/kinopoisk" target="_blank"
                        rel="noopener noreferrer" aria-label="presentation" data-tid="2e9b873"><img
                          src="//avatars.mds.yandex.net/get-bunker/128809/65fe1abdd405eb82aec7490588a1ec6745d9ab87/svg"
                          alt="https://www.youtube.com/user/kinopoisk" loading="lazy" data-tid="9107e4a2"></a>
                    </section>
                    <ul class="footer__content-links styles_contentMenu__OgjQP">
                      <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                          href="https://yandex.ru/jobs/vacancies/dev/?from=kinopoisk&amp;services=kinopoisk" target="_blank"
                          rel="noopener noreferrer" class="footer__content-link styles_contentLink__mRKj9"
                          data-tid="2e9b873">Вакансии</a></li>
                      <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                          href="https://yandex.ru/adv/products/display/kinopoiskmedia" target="_blank" rel="noopener noreferrer"
                          class="footer__content-link styles_contentLink__mRKj9" data-tid="2e9b873">Реклама</a></li>
                      <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a href="/docs/usage/"
                          target="_blank" rel="noopener noreferrer" class="footer__content-link styles_contentLink__mRKj9"
                          data-tid="2e9b873">Соглашение</a></li>
                      <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                          href="https://yandex.ru/support/kinopoisk/index.html" target="_blank" rel="noopener noreferrer"
                          class="footer__content-link styles_contentLink__mRKj9" data-tid="2e9b873">Справка</a></li>
                      <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                          href="/media/rubric/19/" target="_blank" rel="noopener noreferrer"
                          class="footer__content-link styles_contentLink__mRKj9" data-tid="2e9b873">Блог</a></li>
                      <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                          href="https://www.surveygizmo.eu/s3/90259271/" target="_blank" rel="noopener noreferrer"
                          class="footer__content-link styles_contentLink__mRKj9" data-tid="2e9b873">Участие в исследованиях</a>
                      </li>
                      <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                          href="https://kinopoisk.userecho.com/" target="_blank" rel="noopener noreferrer"
                          class="footer__content-link styles_contentLink__mRKj9" data-tid="2e9b873">Предложения</a></li>
                      <li class="footer__content-item styles_contentMenuItem__yTKp2"><button type="button"
                          class="styles_contentButton__Yfvdh">Служба поддержки</button></li>
                    </ul>
                    <div class="styles_root__a_qyh styles_mobileAppsMenu__E1mGj styles_rootDark__ZR7rh" data-tid="358cef48">
                      <a class="styles_store__JFbwQ"
                        href="https://10267.redirect.appmetrica.yandex.com/mainView?appmetrica_tracking_id=170895231946863928"
                        target="_blank" rel="noopener noreferrer" data-tid="7777859c"><img
                          src="//avatars.mds.yandex.net/get-bunker/50064/9de0796ad18834328b4d4858b524bf8ce6f31f98/svg"
                          alt="Загрузить приложение" loading="lazy"></a><a class="styles_store__JFbwQ"
                        href="https://redirect.appmetrica.yandex.com/serve/603240792315703184" target="_blank"
                        rel="noopener noreferrer" data-tid="7777859c"><img
                          src="//avatars.mds.yandex.net/get-bunker/994123/d4d889eb60c34ed8ca7d3c0fe965b8327e229fcf/svg"
                          alt="Загрузить приложение" loading="lazy"></a><a class="styles_store__JFbwQ"
                        href="https://redirect.appmetrica.yandex.com/serve/1179706852124993595" target="_blank"
                        rel="noopener noreferrer" data-tid="7777859c"><img
                          src="//avatars.mds.yandex.net/get-bunker/128809/1b6561563c22de1014279a528719f4f7d9360296/svg"
                          alt="Загрузить приложение" loading="lazy"></a>
                    </div>
                    <section class="styles_bottomSection__qx7AY footer__bottom">
                      <div class="styles_bottomSectionInfo__0XSte footer__bottom-info">
                        <div>
                          <span class="styles_year__tYQPp footer__bottom-info-year">©&nbsp;2003 —
                            <!-- -->2022
                            <!-- -->,
                          </span><a class="styles_bottomSectionInfoLink__Z8Szl footer__bottom-info-link"
                            href="https://www.kinopoisk.ru/" target="_blank" rel="noopener noreferrer"
                            data-tid="2e9b873">Кинопоиск</a><span class="styles_age__sKz6S footer__bottom-info-age">18
                            <!-- -->+
                          </span>
                        </div>
                        <div class="styles_infoName__8KP42">
                          Yandex Service AG
                        </div>
                      </div>
                      <ul class="styles_bottomSectionMenu__kJDDt footer__bottom-links">
                        <li class="styles_bottomSectionMenuItem__RV9c1 footer__bottom-item" data-tid="99325639"><a
                            href="https://tv.yandex.ru" target="_blank" rel="noopener noreferrer"
                            class="styles_bottomSectionMenuLink__oh5dU footer__bottom-link" data-tid="2e9b873">Телепрограмма</a>
                        </li>
                        <li class="styles_bottomSectionMenuItem__RV9c1 footer__bottom-item" data-tid="99325639"><a
                            href="https://music.yandex.ru" target="_blank" rel="noopener noreferrer"
                            class="styles_bottomSectionMenuLink__oh5dU footer__bottom-link" data-tid="2e9b873">Музыка</a></li>
                        <li class="styles_bottomSectionMenuItem__RV9c1 footer__bottom-item" data-tid="99325639"><a
                            href="https://afisha.yandex.ru" target="_blank" rel="noopener noreferrer"
                            class="styles_bottomSectionMenuLink__oh5dU footer__bottom-link" data-tid="2e9b873">Афиша</a></li>
                      </ul>
                      <div class="styles_companySection__2U1gC footer__bottom-company">
                        <span class="styles_companySectionTitle__UUuEV footer__bottom-company-name">Проект компании</span><a
                          class="styles_companyLogo__gDzdb footer__bottom-company-logo" href="https://yandex.ru" target="_blank"
                          rel="noopener noreferrer" data-tid="2e9b873">Яндекс</a>
                      </div>
                    </section>
                  </footer>
                </div>
              </div><button class="styles_root__p7NQg" type="button" data-tid="ed9136fe"><span
                  class="styles_iconWrapper__VsEKC"><span class="styles_icon__6tiLC"></span></span></button>
              <div class="styles_root__yEgpj styles_notifyTooltip___jB7B" data-tid="2f7f876f"></div>
            </div>
            <div class="styles_progress__ZoYH9" hidden data-tid="c2959803">
              <div class="styles_progressBar__p3Spc"></div>
            </div>
          </div>
        </body>

        </html>
    """

    private val movie435html: String = """
        <!doctype html>
        <html>

        <head>
          <meta name="viewport" content="width=device-width">
          <meta charset="utf-8">
          <title data-tid="57f72b5">Зеленая миля — смотреть онлайн — Кинопоиск</title>
          <meta name="theme-color" content="#1f1f1f" data-tid="57f72b5">
          <meta name="apple-mobile-web-app-capable" content="yes" data-tid="57f72b5">
          <meta name="apple-mobile-web-app-status-bar-style" content="black" data-tid="57f72b5">
          <meta name="apple-mobile-web-app-title" content="Кинопоиск" data-tid="57f72b5">
          <meta name="apple-itunes-app" content="app-id=477718890, ct=kp-web, pt=214944, mt=8" data-tid="57f72b5">
          <meta name="application-name" content="Кинопоиск" data-tid="57f72b5">
          <meta property="fb:app_id" content="121953784483000" data-tid="57f72b5">
          <meta property="fb:pages" content="152308956519" data-tid="57f72b5">
          <meta property="og:site_name" content="Кинопоиск" data-tid="57f72b5">
          <meta name="msapplication-TileColor" content="#000" data-tid="57f72b5">
          <meta name="msapplication-TileImage"
            content="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-144.png"
            data-tid="57f72b5">
          <meta name="msapplication-config" content="//st.kp.yandex.net/public/xml/ieconfig.xml" data-tid="57f72b5">
          <link rel="mask-icon"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-16.svg"
            data-tid="57f72b5">
          <link rel="apple-touch-icon-precomposed"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/apple-favicon-152.png"
            data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-57.png" sizes="57x57"
            data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-76.png" sizes="76x76"
            data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-96.png" sizes="96x96"
            data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-120.png"
            sizes="120x120" data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-128.png"
            sizes="128x128" data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-144.png"
            sizes="144x144" data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-152.png"
            sizes="152x152" data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-180.png"
            sizes="180x180" data-tid="57f72b5">
          <link rel="icon" type="image/png"
            href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-196.png"
            sizes="196x196" data-tid="57f72b5">
          <link rel="icon" href="https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-favicon/favicon-64.ico"
            data-tid="57f72b5">
          <link rel="search" type="application/opensearchdescription+xml" title="Поиск на kinopoisk.ru" href="/kp_search.xml"
            data-tid="57f72b5">
          <meta name="description"
            content="В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга. Смотрите онлайн на Кинопоиске."
            data-tid="57f72b5">
          <meta name="keywords"
            content="Зеленая миля — смотреть онлайн — Кинопоиск The Green Mile фильм сериал кино обои фотографии сеансы афиша обзор комментарии рейтинг факты отзывы кадры новости сайт"
            data-tid="57f72b5">
          <link type="application/rss+xml" rel="alternate" title="RSS" href="/news.rss" data-tid="57f72b5">
          <link rel="canonical" href="https://www.kinopoisk.ru/film/435/" data-tid="57f72b5">
          <meta property="og:title" content="«Зеленая миля» (The Green Mile, 1999)" data-tid="57f72b5">
          <meta property="twitter:title" content="«Зеленая миля» (The Green Mile, 1999)" data-tid="57f72b5">
          <meta property="og:description"
            content="В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга. Смотрите онлайн на Кинопоиске."
            data-tid="57f72b5">
          <meta property="twitter:description"
            content="В тюрьме для смертников появляется заключенный с божественным даром. Мистическая драма по роману Стивена Кинга. Смотрите онлайн на Кинопоиске."
            data-tid="57f72b5">
          <meta property="og:url" content="https://www.kinopoisk.ru/film/435/" data-tid="57f72b5">
          <meta property="og:image"
            content="https://avatars.mds.yandex.net/get-kinopoisk-image/6201401/439016dd-d314-4080-acf8-5e571c3efae6/1200x630"
            data-tid="57f72b5">
          <meta property="vk:image"
            content="https://avatars.mds.yandex.net/get-kinopoisk-image/6201401/439016dd-d314-4080-acf8-5e571c3efae6/1200x630"
            data-tid="57f72b5">
          <meta name="twitter:image"
            content="https://avatars.mds.yandex.net/get-kinopoisk-image/6201401/439016dd-d314-4080-acf8-5e571c3efae6/1200x630"
            data-tid="57f72b5">
          <meta property="og:image:width" content="1200" data-tid="57f72b5">
          <meta property="og:image:height" content="630" data-tid="57f72b5">
          <meta name="twitter:site" content="kinopoiskru" data-tid="57f72b5">
          <meta name="twitter:card" content="summary_large_image" data-tid="57f72b5">
          <meta property="og:type" content="website" data-tid="57f72b5">
          <link rel="alternate" href="android-app://ru.kinopoisk/https/www.kinopoisk.ru/film/435/" data-tid="57f72b5">
          <link rel="alternate" href="ios-app://EK7Z26L6D4.ru.kinopoisk/https/www.kinopoisk.ru/film/435/" data-tid="57f72b5">
          <meta name="next-head-count" content="47">
          <link rel="preload" as="script" href="https://yandex.ru/ads/system/context.js" crossorigin="anonymous"
            data-tid="db024bc5">
          <script src="https://yandex.ru/ads/system/context.js" async crossorigin="anonymous"
            data-config="{&amp;quot;COMBO_EXP&amp;quot;:0}" data-tid="3e4d64d9"></script>
          <link rel="manifest"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/pwa-manifests/production.json"
            crossorigin="anonymous" data-tid="74a9c6bc">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/c94162dd6edb9809.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/c94162dd6edb9809.css"
            crossorigin="anonymous" data-n-g="">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/9bb2cddd1d03953c.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/9bb2cddd1d03953c.css"
            crossorigin="anonymous" data-n-p="">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/1457bfb89d2cba8a.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/1457bfb89d2cba8a.css"
            crossorigin="anonymous" data-n-p="">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/080900a337a90d28.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/080900a337a90d28.css"
            crossorigin="anonymous" data-n-p="">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/c2a3191c03d6c7da.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/c2a3191c03d6c7da.css"
            crossorigin="anonymous" data-n-p="">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/f0c1fa89f04171cd.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/f0c1fa89f04171cd.css"
            crossorigin="anonymous" data-n-p="">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/87576cb630dc2062.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/87576cb630dc2062.css"
            crossorigin="anonymous" data-n-p="">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/d2ddd25e213814f0.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/d2ddd25e213814f0.css"
            crossorigin="anonymous">
          <link rel="preload"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/3f399ce3836c5e20.css"
            as="style" crossorigin="anonymous">
          <link rel="stylesheet"
            href="https://yastatic.net/s3/kinopoisk-frontend/frontend-www/release/_next/static/css/3f399ce3836c5e20.css"
            crossorigin="anonymous">
          <noscript data-n-css=""></noscript>
        </head>

        <body class="body" data-tid="f8463833">
          <div id="__next" data-reactroot="">
            <div class="styles_root__hSE6c styles_promoPopover__pKHxa" data-tid="d504712e">
              <button type="button" class="styles_closeButton__LEexp">
                <span class="styles_closeButtonIcon__w69HE"></span>
              </button>
            </div>
            <div class="styles_root__vsmL9 styles_wideRoot__JrXG9" data-tid="2781b874">
              <div class="styles_root__BJH2_ styles_headerContainer__sFBK8" data-tid="1d1e4a8c">
                <header class="styles_root__jlo2L styles_header__0LyFn styles_rootDark__nQDyF" data-tid="63b05890">
                  <div class="header-navigation styles_navigation__S8raz styles_navigationDark__x0vdW">
                    <div class="styles_logoContainer__G47EP">
                      <div class="styles_root__jfc_s" data-tid="911e2f4d">
                        <div class="">
                          <button class="styles_root__coHaQ styles_burger__HcbjK" type="button" data-tid="8ed90190">
                            <span class="styles_icon__J3wes" style="color:#999999"></span>
                          </button>
                          <div class="styles_dropdown__MqT__ styles_dropdownOpen__mTn_V styles_dropdownDefault__KwZHT"
                            data-tid="585eb7c0">
                            <div class="styles_dropdownMenu__bouwC styles_dropdownMenu__c7FZ4 styles_dropdownMenuDark__NzGxR">
                              <div class="styles_navigationMenu__c_jLJ styles_root__RBtQR" data-tid="e500879d">
                                <a href="/" class="styles_root__7mPJN styles_darkThemeItem__E_aGY" data-tid="de7c6530">
                                  <img loading="lazy" class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/128809/4d6f5bd4e839b166859243f82e9fdeb3bc910931/svg"
                                    data-tid="b35288c1">
                                  Главная
                                </a>
                                <a href="https://hd.kinopoisk.ru/" class="styles_root__7mPJN styles_darkThemeItem__E_aGY"
                                  data-tid="de7c6530">
                                  <img loading="lazy" class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/61205/478c72b68bc4ac507483b2676994bbc1df5f05be/svg"
                                    data-tid="b35288c1">Онлайн-кинотеатр
                                </a>
                                <a href="/lists/categories/movies/1/" class="styles_root__7mPJN styles_darkThemeItem__E_aGY"
                                  data-tid="de7c6530">
                                  <img loading="lazy" class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/50064/ab24b8099cb4ca11c08b0def91dc5c1d4fd78649/svg"
                                    data-tid="b35288c1">Фильмы</a>
                                <a href="/lists/categories/movies/3/" class="styles_root__7mPJN styles_darkThemeItem__E_aGY"
                                  data-tid="de7c6530"><img loading="lazy" class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/61205/9daeaf410906b5794685b7b5bb25dfd2c647fccf/svg"
                                    data-tid="b35288c1">Сериалы</a><a href="/afisha/new/"
                                  class="styles_root__7mPJN styles_darkThemeItem__E_aGY" data-tid="de7c6530"><img loading="lazy"
                                    class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/118781/ae7fbfc1773a6bbd61ee0154628c6fe14bf6959e/svg"
                                    data-tid="b35288c1">Билеты в кино</a><a href="/media/"
                                  class="styles_root__7mPJN styles_darkThemeItem__E_aGY" data-tid="de7c6530"><img loading="lazy"
                                    class="styles_icon__PXEHs"
                                    src="https://avatars.mds.yandex.net/get-bunker/118781/960a47a181b1b0a28ceb45a075e64c1a9378442c/svg"
                                    data-tid="b35288c1">Медиа</a>
                              </div>
                            </div>
                          </div>
                        </div>
                        <a href="/" class="styles_root__dYidr styles_logo___bcop" data-tid="d4e8d214">
                          <img class="styles_img__3hWmL kinopoisk-header-logo__img" alt="Кинопоиск"
                            src="data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTY0IiBoZWlnaHQ9IjM2IiBmaWxsPSJub25lIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPjxwYXRoIGZpbGwtcnVsZT0iZXZlbm9kZCIgY2xpcC1ydWxlPSJldmVub2RkIiBkPSJNNTguODU5IDE4YzAtNS44ODkgMi45NTQtMTAuNiA4LjI4MS0xMC42IDUuMzI4IDAgOC4yODEgNC43MTEgOC4yODEgMTAuNiAwIDUuODktMi45NTQgMTAuNi04LjI4IDEwLjYtNS4zMjggMC04LjI4Mi00LjcxLTguMjgyLTEwLjZabTguMjgxIDcuNjZjMi4wNzIgMCAyLjk1NC0zLjUzNCAyLjk1NC03LjY1MiAwLTQuMTItLjg4OS03LjY1Mi0yLjk1NC03LjY1Mi0yLjA2NSAwLTIuOTU0IDMuNTMzLTIuOTU0IDcuNjUyLS4wMDcgNC4xMTguODgyIDcuNjUyIDIuOTU0IDcuNjUyWk0zLjg0MyA3Ljd2NS41OTZoLjI5NEw3Ljk4IDcuN2g1LjMybC03LjA5OCA2LjQ3NC4yOTQuMjkzTDE5LjUxIDcuNjkzdjQuNzExTDcuOTczIDE2LjUyM3YuMjkybDExLjUzNy0xLjAyOHY0LjQxOUw3Ljk3MyAxOS4xNzh2LjI5M2wxMS41MzcgNC4xMTh2NC43MTJMNi40OTYgMjEuNTI2bC0uMjk0LjI5MyA3LjA5OCA2LjQ3NEg3Ljk4bC0zLjg0My01LjU5NmgtLjI5NHY1LjU5NkgwVjcuNjg2aDMuODQzVjcuN1ptMTkuMjMgMEgyOC4xbC0uMjk0IDEyLjM2M2guMjk0TDM0LjAxNSA3LjdoNC40Mzh2MjAuNjA4aC01LjAyNmwuMjk0LTEyLjM2NGgtLjI5NEwyNy41MSAyOC4zMDloLTQuNDM4VjcuN1ptMjMuOTU1IDBoLTUuMDI2djIwLjYwOGg1LjAyNnYtOS4xM2g0LjEzN3Y5LjEzaDUuMDI2VjcuN2gtNS4wMjZ2Ny45NTJoLTQuMTM3VjcuN1ptNDUuMjUgMGgtMTQuMTl2MjAuNjA4aDUuMDI3VjExLjIzM2g0LjEzN3YxNy4wNzVoNS4wMjZWNy43Wm0yLjY2IDEwLjNjMC01Ljg4OSAyLjk1NC0xMC42IDguMjgyLTEwLjYgNS4zMiAwIDguMjgxIDQuNzExIDguMjgxIDEwLjYgMCA1Ljg5LTIuOTU0IDEwLjYtOC4yODEgMTAuNi01LjMyIDAtOC4yODItNC43MS04LjI4Mi0xMC42Wm04LjI4MiA3LjY2YzIuMDcyIDAgMi45NTQtMy41MzQgMi45NTQtNy42NTIgMC00LjEyLS44ODktNy42NTItMi45NTQtNy42NTItMi4wNzIgMC0yLjk1NCAzLjUzMy0yLjk1NCA3LjY1MiAwIDQuMTE4Ljg4MiA3LjY1MiAyLjk1NCA3LjY1MlpNMTE5LjE4NyA3LjdoLTUuMDI2djIwLjYwOGg0LjQzOGw1LjkxNi0xMi4zNjRoLjI5NGwtLjI5NCAxMi4zNjRoNS4wMjZWNy43aC00LjQzOGwtNS45MTYgMTIuMzYzaC0uMjk0bC4yOTQtMTIuMzYzWm0yMy42NjkgMTMuNTQxIDQuNzMyLjU4NWMtLjg4OSA0LjEyLTIuOTU0IDYuNzc0LTcuMzY0IDYuNzc0LTUuMzIgMC04LjAxNi00LjcxLTguMDE2LTEwLjYgMC01Ljg4OSAyLjY4OS0xMC42IDguMDE2LTEwLjYgNC4zMTcgMCA2LjQ3NSAyLjY0OSA3LjM2NCA2LjQ3NWwtNC43MzIgMS4xNzdjLS4yOTQtMi4wNjMtMS4xNTUtNC43MS0yLjYzMi00LjcxLTEuNzcxIDAtMi42ODkgMy41MzMtMi42ODkgNy42NTEgMCA0LjA5LjkxOCA3LjY1MiAyLjY4OSA3LjY1MiAxLjQ0OS4wMTUgMi4zMy0yLjM0MSAyLjYzMi00LjQwNFptMTEuODMtMTMuNTRoLTQuNzMydjIwLjYwN2g0LjczMnYtOS4xM2guMjk0bDMuNTQ5IDkuMTNIMTY0bC01LjE3Ny0xMC42TDE2My44NDkgNy43aC01LjAyNmwtMy44NDMgOS4xM2gtLjI5NFY3LjdaIiBmaWxsPSIjZmZmIi8+PC9zdmc+Cg=="
                            data-tid="79e4350">
                        </a>
                      </div>
                    </div>
                    <div class="styles_mainContainer__faOVn">
                      <div class="styles_featureMenuContainer__KbrzA">
                        <nav class="styles_root__DR_oz kinopoisk-header-featured-menu styles_adaptive__F508O"
                          data-tid="78f04c5d">
                          <a class="styles_root__hBoYg styles_item__HaqiK kinopoisk-header-featured-menu__item"
                            href="https://hd.kinopoisk.ru/" target="_self" data-tid="acc26a70">
                            <img aria-label="presentation" class="styles_iconHover__UMGd0 styles_icon__IXP4s"
                              src="https://avatars.mds.yandex.net/get-bunker/118781/5e4a451dabd5982b775db20bc084cc215fd0e14a/svg"
                              srcset="" data-tid="b35288c1">
                            <img aria-label="presentation" class="styles_icon__IXP4s"
                              src="https://avatars.mds.yandex.net/get-bunker/61205/70cc2c1c559189c3139a315b1d06db38faefa2b5/svg"
                              srcset="" data-tid="b35288c1">Онлайн-кинотеатр
                          </a>
                          <a class="styles_root__hBoYg styles_item__HaqiK kinopoisk-header-featured-menu__item"
                            href="https://www.kinopoisk.ru/special/smarttv_instruction?utm_source=kinopoisk&amp;utm_medium=selfpromo_kp&amp;utm_campaign=button_header"
                            target="_blank" data-tid="acc26a70">
                            <img aria-label="presentation" class="styles_iconHover__UMGd0 styles_icon__IXP4s"
                              src="https://avatars.mds.yandex.net/get-bunker/50064/9bd69a8ca16bd1fa7395dba2ab3082c4bebd306c/svg"
                              srcset="" data-tid="b35288c1">
                            <img aria-label="presentation" class="styles_icon__IXP4s"
                              src="https://avatars.mds.yandex.net/get-bunker/61205/c7ca1a7300068a2cf01c57a1f351ba9d89c20ee3/svg"
                              srcset="" data-tid="b35288c1">
                            Установить на ТВ
                          </a>
                        </nav>
                      </div>
                      <div class="styles_searchFormContainer__GyAL5">
                        <div class="styles_searchForm__AIMFU styles_searchFormDefault__QNml_" data-tid="8d7d7cbd">
                          <form class="styles_form__i86wS" action="/index.php">
                            <div class="styles_root__dTeXi styles_searchInputElement__qNbS4" data-tid="b0e8f9b">
                              <input type="text" name="kp_query"
                                class="styles_input__4vNAb kinopoisk-header-search-form-input__input" autocomplete="off"
                                aria-label="Фильмы, сериалы, персоны" placeholder="Фильмы, сериалы, персоны" value="" required>
                              <div class="styles_controlContainer__5VetH kinopoisk-header-search-form-input__control-container">
                                <a href="/s/" class="styles_advancedSearch__uwvnd" aria-label="advanced-search" tabindex="-1">
                                  <svg class="styles_advancedSearchIcon__Zxjax" xmlns="http://www.w3.org/2000/svg" width="18"
                                    height="18" viewbox="0 0 18 18">
                                    <path fill="#000" fill-rule="evenodd"
                                      d="M5.995 10.3A2.7 2.7 0 0 1 8.504 12H17v2H8.504a2.7 2.7 0 0 1-5.018 0H1v-2h2.486a2.7 2.7 0 0 1 2.509-1.7zm0 1.7a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm5.997-8.7A2.7 2.7 0 0 1 14.5 5H17v2h-2.5a2.7 2.7 0 0 1-5.017 0H1V5h8.483a2.7 2.7 0 0 1 2.509-1.7zm0 1.7a1 1 0 1 0 0 2 1 1 0 0 0 0-2z">
                                    </path>
                                  </svg>
                                </a>
                                <button type="submit" class="styles_root__CUh_v styles_submit__2AIpj" aria-label="submit"
                                  tabindex="-1" data-tid="f49ca51f">
                                  <svg class="styles_icon__1bYKL search-form-submit-button__icon"
                                    xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewbox="0 0 18 18">
                                    <path fill="#000" fill-rule="evenodd"
                                      d="M12.026 10.626L16 14.6 14.6 16l-3.974-3.974a5.5 5.5 0 1 1 1.4-1.4zM7.5 11.1a3.6 3.6 0 1 0 0-7.2 3.6 3.6 0 0 0 0 7.2z">
                                    </path>
                                  </svg>
                                  Найти
                                </button>
                              </div>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                    <div class="styles_userContainer__hLiRQ">
                      <button type="button" class="styles_searchButton__kXYs6">
                        <span class="styles_searchButtonIconSmall__poB4V"></span>
                      </button>
                      <div class="styles_root__JgDCj" data-tid="1a82203d">
                        <div>
                          <a class="styles_plusDesktopBrandingButton__X4z6T styles_root__OkfMY styles_padding4x14__J5WqH styles_root__lbhjd styles_rootLight__4o4CJ styles_rootPlus__Xx9Yy"
                            href="https://hd.kinopoisk.ru/?source=kinopoisk_head_button">
                            Попробовать Плюс
                          </a>
                        </div>
                        <button type="button" class="styles_loginButton__LWZQp">
                          Войти
                        </button>
                      </div>
                    </div>
                  </div>
                </header>
              </div>
              <div class="styles_wideContentContainer__lu_K3">
                <div class="styles_root__B1q5W styles_rootDark__L1f7i styles_root__axj8R" data-tid="21855542">
                  <div class="styles_background__ME0M5">
                    <div class="styles_root__QOd_V" data-tid="59e98289">
                      <div class="styles_root__8tScN styles_cover__e29Y0" data-tid="408432e9">
                        <img class="styles_image__rXBJa image styles_root__DZigd styles_rootNotLoaded__57tgq"
                          src="//avatars.mds.yandex.net/get-ott/224348/2a00000169e39ef77f588ccdfe574dae8227/1344x756"
                          srcset="//avatars.mds.yandex.net/get-ott/224348/2a00000169e39ef77f588ccdfe574dae8227/1344x756 1x, //avatars.mds.yandex.net/get-ott/224348/2a00000169e39ef77f588ccdfe574dae8227/2688x1512 2x"
                          data-tid="d813cf42">
                        <div class="styles_gradient__ELWDc"></div>
                      </div>
                    </div>
                  </div>
                  <div class="styles_root__UtArQ" data-tid="3716659c">
                    <div class="styles_root__2kxYy" data-tid="914bd01c">
                      <div class="styles_column__r2MWX styles_md_6__XDxd6 styles_lg_8__7Mdim styles_column__5dEFP"
                        data-tid="893da4ad">
                        <div class="styles_sidebar__mZOfP">
                          <div class="styles_root__JykRA styles_basicMediaSection__l88k1 styles_basicMediaSectionDark__2YiY5"
                            data-tid="be907ee0">
                            <div class="styles_posterContainer__F02wH">
                              <div class="styles_root__0qoat" data-tid="fe27f3c4">
                                <a class="styles_posterLink__C1HRc" href="/film/435/posters/">
                                  <img class="film-poster styles_root__24Jga styles_rootInDark__64LVq image styles_root__DZigd"
                                    alt="Зеленая миля (The Green Mile)"
                                    src="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/4057c4b8-8208-4a04-b169-26b0661453e3/300x450"
                                    srcset="//avatars.mds.yandex.net/get-kinopoisk-image/1599028/4057c4b8-8208-4a04-b169-26b0661453e3/300x450 1x, //avatars.mds.yandex.net/get-kinopoisk-image/1599028/4057c4b8-8208-4a04-b169-26b0661453e3/600x900 2x"
                                    data-tid="d813cf42">
                                </a>
                                <a class="styles_soundtrackButton__MiEe0 styles_soundtrackButton__D5WNc"
                                  href="/film/435/tracks/" data-tid="4113d3fe">
                                  soundtracks
                                </a>
                              </div>
                            </div>
                            <div class="styles_trailerContainer__OrL6j styles_section__OVMys">
                              <div class="film-trailer styles_rootInDark__Yb_ii styles_rootSmSize__SXey8" data-tid="cc89b13d">
                                <div role="button" tabindex="0" class="styles_previewWithAction__24bFH styles_preview__ruOp9">
                                  <img class="styles_previewImg__zhMic image styles_root__DZigd" alt="Трейлер (русский язык)"
                                    src="//avatars.mds.yandex.net/get-kino-vod-films-gallery/1668876/a345b127722243984f01ef6504c9a477/100x64_3"
                                    srcset="//avatars.mds.yandex.net/get-kino-vod-films-gallery/1668876/a345b127722243984f01ef6504c9a477/100x64_3 1x, //avatars.mds.yandex.net/get-kino-vod-films-gallery/1668876/a345b127722243984f01ef6504c9a477/600x380 2x"
                                    data-tid="d813cf42">
                                  <div class="styles_previewInfo__fZqll styles_mainTrailerPreviewInfo__6fFSL">
                                    <button type="button" class="styles_root__2V17R" data-tid="f1f187d8">Трейлер</button>
                                    <span class="styles_duration__BiWBm">2:28</span>
                                  </div>
                                </div>
                                <a class="styles_title__vd96O" href="/film/435/video/13494/">
                                  Трейлер (русский язык)
                                </a>
                                <span class="styles_date__d5xwh">16 июня 2009</span>
                              </div>
                            </div>
                            <div class="styles_userControlsContainer__iYP9P styles_section__OVMys">
                              <div class="styles_controlContainer__5hjSk" data-tid="5310ddc0">
                                <div class="styles_foldersMenu__R90ST styles_root__g0CT9" data-tid="4a36b453">
                                  <div class="styles_root__VEPvG styles_buttonSet____zRE styles_rootDark__21i2y"
                                    data-tid="62abee53">
                                    <button
                                      class="styles_button__MpYNC styles_listToWatchButton__N_ywG styles_root__sjOi_ styles_rootDark__q3mrJ styles_rootWithTitle__F2vRG styles_root__lbhjd styles_rootDark__NnSuo styles_rootGhost__7yeKn">
                                      Буду смотреть
                                    </button>
                                    <button
                                      class="styles_button__MpYNC styles_root__v9qoq styles_rootDark__0JBC5 styles_root__lbhjd styles_rootDark__NnSuo styles_rootGhost__7yeKn">
                                      <span class="styles_tooltip__BZYE_" data-tid="d43912a6">Добавить в список</span>
                                    </button>
                                  </div>
                                </div>
                              </div>
                              <div class="styles_rootDark__DGVpw styles_root__dZcuU" data-tid="e5bec5b3">
                                <a href="/film/435/folders/" class="styles_linkDark__MhXy4 styles_link___yiZO">
                                  Все папки пользователей
                                </a>
                              </div>
                            </div>
                            <div class="styles_socialControlsContainer__7mYQK">
                              <div class="styles_root__2kxYy" data-tid="914bd01c">
                                <div class="styles_column__r2MWX styles_md_6__XDxd6 styles_lg_8__7Mdim" data-tid="893da4ad">
                                  <div class="styles_share__4uBuh styles_root__MJ3vO" data-tid="755217e">
                                    <div id="film-share-buttons"></div>
                                    <div class="styles_root__vr1TL" data-tid="7cfcb140">
                                      <span
                                        class="styles_button__BSoLb styles_root__2Vgyb styles_rootInDark__qcybG styles_root__2Vgyb"
                                        data-tid="ad14a6be">‌</span>
                                      <span
                                        class="styles_button__BSoLb styles_root__2Vgyb styles_rootInDark__qcybG styles_root__2Vgyb"
                                        data-tid="ad14a6be">‌</span>
                                      <span
                                        class="styles_button__BSoLb styles_root__2Vgyb styles_rootInDark__qcybG styles_root__2Vgyb"
                                        data-tid="ad14a6be">‌</span>
                                      <span
                                        class="styles_button__BSoLb styles_root__2Vgyb styles_rootInDark__qcybG styles_root__2Vgyb"
                                        data-tid="ad14a6be">‌</span>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="styles_root__2kxYy" data-tid="914bd01c">
                                <div
                                  class="styles_column__r2MWX styles_md_6__XDxd6 styles_lg_8__7Mdim styles_root__wNLLi styles_feedbackButtons__i2cPM"
                                  data-tid="893da4ad">
                                  <div class="styles_wrapper__rcDa_" data-tid="d43e8a06">
                                    <button type="button"
                                      class="styles_buttonError__P0JjC styles_button__qRXLB styles_buttonDark__qLSwL">
                                      Нашли ошибку?
                                    </button>
                                    <button type="button"
                                      class="styles_buttonInfo__gH8CS styles_button__qRXLB styles_buttonDark__qLSwL">
                                      Добавить инфо
                                    </button>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="styles_delimiter__m7GQp styles_delimiterDark__kHzRL styles_delimiter__rPlVI"></div>
                      <div class="styles_column__r2MWX styles_md_17__FaWtp styles_lg_21__YjFTk styles_column__5dEFP"
                        data-tid="893da4ad">
                        <div class="styles_main__vjk2Q styles_main__ZXV8U">
                          <div class="styles_root__4VfvJ styles_basicInfoSection__EiD2J styles_basicInfoSectionDark__Mxqmc"
                            data-tid="bb43fc51">
                            <div class="styles_root__2kxYy styles_topLine__xigow" data-tid="914bd01c">
                              <div class="styles_column__r2MWX styles_md_11__UdIH_ styles_lg_15__Ai53P" data-tid="893da4ad">
                                <div class="styles_header__mzj3d">
                                  <div class="styles_title__hTCAr" data-tid="b97a4e4c">
                                    <h1
                                      class="styles_title__65Zwx styles_root__l9kHe styles_root__5sqsd styles_rootInDark__SZlor"
                                      itemprop="name"><span data-tid="75209b22">Зеленая миля (1999)</span></h1>
                                    <div class="styles_root__LIL2v styles_rootInDark__wvz_c" data-tid="7cdbd36a">
                                      <span class="styles_originalTitle__JaNKM" data-tid="eb6be89">The Green Mile</span><span
                                        class="styles_rootSmallFaded__LiPsm styles_rootSmallFadedInDark__m1H_w"
                                        data-tid="5c1ffa33">16+</span>
                                    </div>
                                  </div>
                                  <div class="styles_root__VAeAD" data-tid="98a3cfe2">
                                    <div class="styles_topText__p__5L">
                                      <p class="styles_root__aZJRN" data-tid="bfd38da2">В тюрьме для смертников появляется
                                        заключенный с божественным даром. Мистическая драма по роману Стивена Кинга</p>
                                    </div>
                                    <div class="styles_watchOnlineBlock__BE3Ci" data-tid="25c2fa6f">
                                      <div class="styles_subscriptionText__xEiOR">
                                        Первый месяц — бесплатно
                                      </div>
                                      <div class="styles_subscriptionSubtext__zQuOI">
                                        далее 199 KGS в месяц
                                      </div>
                                      <div class="styles_buttonsContainer__i6y3F">
                                        <div class="styles_button__Q82i0">
                                          <div class="watch-online-button styles_containerRoot__cSJvu" data-tid="85a13d20">
                                            <a href="https://hd.kinopoisk.ru/film/42e511a460839b298d96effd8de60c68?from_block=kp-button-online"
                                              class="kinopoisk-watch-online-button styles_root__EZXGw styles_rootPlus__bBjkI styles_rootDesktop__fGTTz styles_rootSizeH44__g0ZUk styles_isRounded__fiuxG styles_watchOnlineButton__ruFtI"><span
                                                class="styles_defaultText__PgVb9 undefined" data-tid="6cb8d12f">По подписке
                                                Плюс</span></a>
                                          </div>
                                        </div>
                                        <div class="styles_button__Q82i0">
                                          <div class="style_root__BmiQ7" data-tid="be0d3e42">
                                            <button
                                              class="style_button__LAvI6 style_buttonLarge__pneTU style_buttonDefault__c0tGZ style_buttonDark__78ypq style_withIconLeft__xpAII style_onlyIcon__D09QE"
                                              title="Буду смотреть"><span class="style_iconLeft__vU_kH"
                                                data-tid="c8f29373"><span
                                                  class="style_icon__QLJtP style_iconDark__fxZF9"></span></span></button>
                                          </div>
                                        </div>
                                        <div class="styles_button__Q82i0">
                                          <div class="style_root__eRD4o" data-tid="17569662">
                                            <div class="style_root__Bt5S1" data-tid="818a5033">
                                              <button
                                                class="style_button__LAvI6 style_buttonLarge__pneTU style_buttonDefault__c0tGZ style_buttonDark__78ypq style_withIconLeft__xpAII style_onlyIcon__D09QE"><span
                                                  class="style_iconLeft__vU_kH" data-tid="c8f29373"><span
                                                    class="style_icon__V3VQE style_dropdownButtonIconDark__sDaV0"
                                                    data-tid="e07f9f7b"></span></span></button>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                    <div class="styles_hdMetaTableContainer__6BtEF">
                                      <div class="" data-test-id="meta-table" data-tid="bd126b5e">
                                        <div
                                          class="styles_rowLight__P8Y_1 styles_row__da_RK styles_row__lwy5i styles_withDelimiter__ay80B styles_movieDetails__FOUgq"
                                          data-tid="7cda04a5">
                                          <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                            Аудиодорожки
                                          </div>
                                          <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="e1e37c21">
                                            <div class="styles_valueLight__UMEMJ">
                                              Русский, Английский
                                            </div>
                                          </div>
                                        </div>
                                        <div
                                          class="styles_rowLight__P8Y_1 styles_row__da_RK styles_row__lwy5i styles_withDelimiter__ay80B styles_movieDetails__FOUgq"
                                          data-tid="7cda04a5">
                                          <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                            Субтитры
                                          </div>
                                          <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="e1e37c21">
                                            <div class="styles_valueLight__UMEMJ">
                                              Русские
                                            </div>
                                          </div>
                                        </div>
                                        <div
                                          class="styles_rowLight__P8Y_1 styles_row__da_RK styles_row__lwy5i styles_definitionRow__fkueN styles_withDelimiter__ay80B styles_movieDetails__FOUgq"
                                          data-tid="7cda04a5">
                                          <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                            Качество видео
                                          </div>
                                          <div
                                            class="styles_valueLight__nAaO3 styles_value__g6yP4 styles_definitionRowValue__QvGno"
                                            data-tid="7e7b470c">
                                            <svg width="50" height="16" fill="none" xmlns="http://www.w3.org/2000/svg"
                                              data-tid="43b9c55a">
                                              <rect x=".75" y=".75" width="48.5" height="14.5" rx="2.25" stroke="#ccc"
                                                stroke-width="1.5"></rect>
                                              <path
                                                d="M6.768 12.12H8.55V9.073h2.585V7.73H8.55V5.65h3.267V4.256h-5.05v7.865ZM14.565 12.24c.946 0 1.529-.44 1.826-1.022v.902h1.584V6.367H16.39v3.377c0 .847-.528 1.265-1.2 1.265-.681 0-1-.374-1-1.155V6.367h-1.584v3.707c0 1.507.836 2.167 1.958 2.167ZM19.43 12.12h1.584V3.76H19.43v8.36ZM22.513 12.12h1.584V3.76h-1.584v8.36ZM28.134 12.12h1.782V8.875h3.157v3.245h1.782V4.255h-1.782v3.212h-3.157V4.255h-1.782v7.865ZM36.534 12.12h2.563c2.761 0 4.136-1.573 4.136-3.927v-.088c0-2.354-1.364-3.85-4.125-3.85h-2.574v7.865Zm1.782-1.386V5.64h.704c1.661 0 2.354.869 2.354 2.486v.088c0 1.628-.748 2.519-2.332 2.519h-.726Z"
                                                fill="#ccc"></path>
                                            </svg>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="styles_watchingServicesOnline__ZlcNv">
                                    <div data-tid="e8c5d94f">
                                      <div class="style_header__ZPNFN" data-tid="c456f2ce">
                                        <h3
                                          class="film-page-section-title styles_rootTitle__2g8Sk style_title__DoU6X styles_rootXxsm__Jjccw styles_root__B8zR6 styles_rootLight__m9pzE">
                                          <div class="styles_titleWithMoreItem__rTA7s" data-tid="6a319a9e">
                                            Где ещё смотреть<span class="styles_moreItem__U5bzS">7</span>
                                          </div>
                                        </h3><button class="style_toggleButton__QSI86 style_toggleButtonDark__8TpjK"
                                          aria-label="Развернуть" type="button"></button>
                                      </div>
                                      <div class="style_contentWrapper__iT6JH" data-tid="d0682e1e">
                                        <div class="style_content__L6r_p">
                                          <div class="style_listInRows__op0dF" data-tid="c456f2ce">
                                            <div class="style_item__coMvy" data-tid="c456f2ce">
                                              <a href="https://okko.tv/movie/the-green-mile?utm_medium=referral&amp;utm_source=yandex_search&amp;utm_campaign=new_search_feed"
                                                target="_blank" rel="noopener noreferrer"
                                                class="styles_root__b5Uwf styles_rootDark__xvd5U" data-tid="5f829942"><span
                                                  class="styles_logo__SNt5e"><img
                                                    class="style_root__KgLcy image styles_root__DZigd"
                                                    src="//avatars.mds.yandex.net/get-ott/239697/7713e586-17d1-42d1-ac62-53e9ef1e70c3/orig"
                                                    srcset="//avatars.mds.yandex.net/get-ott/239697/7713e586-17d1-42d1-ac62-53e9ef1e70c3/orig 1x, //avatars.mds.yandex.net/get-ott/239697/7713e586-17d1-42d1-ac62-53e9ef1e70c3/orig 2x"
                                                    data-tid="d813cf42"></span><span class="styles_title__434hO">Okko</span></a>
                                            </div>
                                            <div class="style_item__coMvy" data-tid="c456f2ce">
                                              <a href="https://www.ivi.ru/watch/90283?utm_source=yandex&amp;utm_medium=wizard"
                                                target="_blank" rel="noopener noreferrer"
                                                class="styles_root__b5Uwf styles_rootDark__xvd5U" data-tid="5f829942"><span
                                                  class="styles_logo__SNt5e"><img
                                                    class="style_root__KgLcy image styles_root__DZigd"
                                                    src="//avatars.mds.yandex.net/get-ott/1672343/a8dc1dd4-e7b2-4984-ad33-242b79cb5307/orig"
                                                    srcset="//avatars.mds.yandex.net/get-ott/1672343/a8dc1dd4-e7b2-4984-ad33-242b79cb5307/orig 1x, //avatars.mds.yandex.net/get-ott/1672343/a8dc1dd4-e7b2-4984-ad33-242b79cb5307/orig 2x"
                                                    data-tid="d813cf42"></span><span class="styles_title__434hO">IVI</span></a>
                                            </div>
                                            <div class="style_item__coMvy" data-tid="c456f2ce">
                                              <a href="https://premier.one/show/16086?utm_source=yandex&amp;utm_medium=yandex_feed_search&amp;utm_campaign=yandex_feed"
                                                target="_blank" rel="noopener noreferrer"
                                                class="styles_root__b5Uwf styles_rootDark__xvd5U" data-tid="5f829942"><span
                                                  class="styles_logo__SNt5e"><img
                                                    class="style_root__KgLcy image styles_root__DZigd"
                                                    src="//avatars.mds.yandex.net/get-ott/239697/0f86e907-9531-47e9-87bd-5101a08d4e30/orig"
                                                    srcset="//avatars.mds.yandex.net/get-ott/239697/0f86e907-9531-47e9-87bd-5101a08d4e30/orig 1x, //avatars.mds.yandex.net/get-ott/239697/0f86e907-9531-47e9-87bd-5101a08d4e30/orig 2x"
                                                    data-tid="d813cf42"></span><span
                                                  class="styles_title__434hO">PREMIER</span></a>
                                            </div>
                                            <div class="style_item__coMvy" data-tid="c456f2ce">
                                              <a href="https://more.tv/zelenaya-milya?utm_source=yandex-snippet&amp;utm_medium=snippet&amp;utm_campaign=zelenaya_milya"
                                                target="_blank" rel="noopener noreferrer"
                                                class="styles_root__b5Uwf styles_rootDark__xvd5U" data-tid="5f829942"><span
                                                  class="styles_logo__SNt5e"><img
                                                    class="style_root__KgLcy image styles_root__DZigd"
                                                    src="//avatars.mds.yandex.net/get-ott/1648503/97e3cbbd-40ee-4298-888d-ed2d0f022a69/orig"
                                                    srcset="//avatars.mds.yandex.net/get-ott/1648503/97e3cbbd-40ee-4298-888d-ed2d0f022a69/orig 1x, //avatars.mds.yandex.net/get-ott/1648503/97e3cbbd-40ee-4298-888d-ed2d0f022a69/orig 2x"
                                                    data-tid="d813cf42"></span><span
                                                  class="styles_title__434hO">more.tv</span></a>
                                            </div>
                                            <div class="style_item__coMvy" data-tid="c456f2ce">
                                              <a href="https://tv.apple.com/ru/movie/%D0%B7%D0%B5%D0%BB%D0%B5%D0%BD%D0%B0%D1%8F-%D0%BC%D0%B8%D0%BB%D1%8F/umc.cmc.7hcm0jk7o0ko95ghs171h7v41"
                                                target="_blank" rel="noopener noreferrer"
                                                class="styles_root__b5Uwf styles_rootDark__xvd5U" data-tid="5f829942"><span
                                                  class="styles_logo__SNt5e"><img
                                                    class="style_root__KgLcy image styles_root__DZigd"
                                                    src="//avatars.mds.yandex.net/get-ott/239697/0ba59459-f965-4089-9e26-4aa9184c7262/orig"
                                                    srcset="//avatars.mds.yandex.net/get-ott/239697/0ba59459-f965-4089-9e26-4aa9184c7262/orig 1x, //avatars.mds.yandex.net/get-ott/239697/0ba59459-f965-4089-9e26-4aa9184c7262/orig 2x"
                                                    data-tid="d813cf42"></span><span class="styles_title__434hO">Apple
                                                  TV</span></a>
                                            </div>
                                            <div class="style_item__coMvy" data-tid="c456f2ce">
                                              <a href="https://kino.tricolor.tv/watch/zelenaya-milya-1999/?utm_source=yandex&amp;utm_medium=feed"
                                                target="_blank" rel="noopener noreferrer"
                                                class="styles_root__b5Uwf styles_rootDark__xvd5U" data-tid="5f829942"><span
                                                  class="styles_logo__SNt5e"><img
                                                    class="style_root__KgLcy image styles_root__DZigd"
                                                    src="//avatars.mds.yandex.net/get-ott/239697/947e777c-2f73-4cbc-b09d-6bfa3966ba13/orig"
                                                    srcset="//avatars.mds.yandex.net/get-ott/239697/947e777c-2f73-4cbc-b09d-6bfa3966ba13/orig 1x, //avatars.mds.yandex.net/get-ott/239697/947e777c-2f73-4cbc-b09d-6bfa3966ba13/orig 2x"
                                                    data-tid="d813cf42"></span><span class="styles_title__434hO">Триколор Кино и
                                                  ТВ</span></a>
                                            </div>
                                            <div class="style_item__coMvy" data-tid="c456f2ce">
                                              <a href="https://wink.ru/media_items/54962552?utm_source=yandex&amp;utm_medium=koldunschick&amp;utm_content=name"
                                                target="_blank" rel="noopener noreferrer"
                                                class="styles_root__b5Uwf styles_rootDark__xvd5U" data-tid="5f829942"><span
                                                  class="styles_logo__SNt5e"><img
                                                    class="style_root__KgLcy image styles_root__DZigd"
                                                    src="//avatars.mds.yandex.net/get-ott/1672343/54096cbe-cc3b-41c9-8e44-990ebbca8d61/orig"
                                                    srcset="//avatars.mds.yandex.net/get-ott/1672343/54096cbe-cc3b-41c9-8e44-990ebbca8d61/orig 1x, //avatars.mds.yandex.net/get-ott/1672343/54096cbe-cc3b-41c9-8e44-990ebbca8d61/orig 2x"
                                                    data-tid="d813cf42"></span><span class="styles_title__434hO">Wink</span></a>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="styles_column__r2MWX styles_md_6__XDxd6 styles_lg_6__eGSDb" data-tid="893da4ad">
                                <div class="styles_root__lMV74 styles_filmRating__H_B11" data-tid="86be324">
                                  <div class="film-rating styles_root__7rVf_ styles_rootMSize__B8Ch0 styles_rootInDark__Mfeeo"
                                    data-tid="71598065">
                                    <div class="styles_ratingValue__UO6Zl styles_rootMSize__B8Ch0">
                                      <div class="styles_valueBlock___nWKb">
                                        <span class="styles_value__N2Vzt"><span
                                            class="film-rating-value styles_rootPositive__mLBSO styles_rootInDark__Janz7">9.1</span></span>
                                      </div>
                                      <div class="styles_countBlock__jxRDI">
                                        <span class="styles_count__iOIwD">762 061 оценка</span>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="styles_kinopoiskRatingSnippet__tqtsG">
                                    <div class="style_root__tg2Nx" data-tid="410c06ef">
                                      <button
                                        class="style_button__LAvI6 style_buttonMedium__Z93fP style_buttonDefault__c0tGZ style_buttonDark__78ypq style_fullWidth__ib6MF">Оценить
                                        фильм</button>
                                    </div>
                                  </div>
                                  <div class="styles_reviewsLink__5xOtO">
                                    <div class="styles_reviewCountDark__5LMtp styles_reviewCount__w_RrM" data-tid="d87cf2dd">
                                      473 рецензии
                                    </div>
                                  </div>
                                </div>
                                <div class="styles_topList__1xtLA styles_topListDark__30bOZ" data-tid="d3ba9b85">
                                  <a class="styles_root__UjpdS styles_rootDark__kCZC3" href="/lists/movies/top250/"
                                    data-tid="2c52e3fd">
                                    <div class="styles_root__ixhBE styles_icon__0l7mg styles_size41x42__BC3fA"
                                      data-tid="a757238c"></div><span class="styles_root__Q2THk styles_listTitle__mDgWK"
                                      data-tid="b012c88c">топ 250</span><span
                                      class="styles_root__Q2THk styles_listPosition__Tg7NC" data-tid="b012c88c">1 место</span>
                                  </a>
                                </div>
                              </div>
                            </div>
                            <div class="styles_root__2kxYy styles_topLine__xigow" data-tid="914bd01c">
                              <div class="styles_column__r2MWX styles_md_11__UdIH_ styles_lg_15__Ai53P" data-tid="893da4ad">
                                <h3
                                  class="film-page-section-title styles_rootTitle__2g8Sk styles_tableHeader__HdxpN styles_rootMd__7Q1_t styles_root__B8zR6 styles_rootLight__m9pzE">
                                  О фильме</h3>
                                <div class="" data-test-id="encyclopedic-table" data-tid="bd126b5e">
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Год производства
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="cfbe5a01">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk"
                                        href="/lists/movies/year--1999/?b=films&amp;b=top">1999</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Страна
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="d5ff4cc">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk"
                                        href="/lists/movies/country--1/?b=films&amp;b=top" data-tid="603f73a4">США</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Жанр
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4 styles_root__5PEXQ"
                                      data-tid="28726596">
                                      <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="d5ff4cc">
                                        <a class="styles_linkLight__cha3C styles_link__3QfAk"
                                          href="/lists/movies/genre--drama/?b=films&amp;b=top" data-tid="603f73a4">драма</a>, <a
                                          class="styles_linkLight__cha3C styles_link__3QfAk"
                                          href="/lists/movies/genre--crime/?b=films&amp;b=top" data-tid="603f73a4">криминал</a>
                                      </div><a href="/film/435/keywords/"
                                        class="styles_linkLight__cha3C styles_link__3QfAk keywords">слова</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Слоган
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="e1e37c21">
                                      <div class="styles_valueLight__nAaO3 styles_value__g6yP4">
                                        «Пол Эджкомб не верил в чудеса. Пока не столкнулся с одним из них»
                                      </div>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Режиссер
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="d5ff4cc">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk" href="/name/24262/"
                                        data-tid="603f73a4">Фрэнк Дарабонт</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Сценарий
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="d5ff4cc">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk" href="/name/24262/"
                                        data-tid="603f73a4">Фрэнк Дарабонт</a>, <a
                                        class="styles_linkLight__cha3C styles_link__3QfAk" href="/name/24263/"
                                        data-tid="603f73a4">Стивен Кинг</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Продюсер
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="d5ff4cc">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk" href="/name/24262/"
                                        data-tid="603f73a4">Фрэнк Дарабонт</a>, <a
                                        class="styles_linkLight__cha3C styles_link__3QfAk" href="/name/24283/"
                                        data-tid="603f73a4">Дэвид Валдес</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Оператор
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="d5ff4cc">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk" href="/name/609246/"
                                        data-tid="603f73a4">Дэвид Тэттерсолл</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Композитор
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="d5ff4cc">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk" href="/name/608629/"
                                        data-tid="603f73a4">Томас Ньюман</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Художник
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="d5ff4cc">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk" href="/name/137866/"
                                        data-tid="603f73a4">Теренс Марш</a>, <a
                                        class="styles_linkLight__cha3C styles_link__3QfAk" href="/name/1999763/"
                                        data-tid="603f73a4">Уильям Крус</a>, <a
                                        class="styles_linkLight__cha3C styles_link__3QfAk" href="/name/1107287/"
                                        data-tid="603f73a4">Керин Вагнер</a>, <a href="/film/435/cast/who_is/design/"
                                        class="styles_linkLight__cha3C styles_link__3QfAk">...</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Монтаж
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="d5ff4cc">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk" href="/name/1986116/"
                                        data-tid="603f73a4">Ричард Фрэнсис-Брюс</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Бюджет
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="cfbe5a01">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk"
                                        href="/film/435/box/">${'$'}60&nbsp;000&nbsp;000</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Маркетинг
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="cfbe5a01">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk"
                                        href="/film/435/box/">${'$'}30&nbsp;000&nbsp;000</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Сборы в США
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4 styles_root__XwglO"
                                      data-tid="41068c56">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk"
                                        href="/film/435/box/">${'$'}136&nbsp;801&nbsp;374</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Сборы в мире
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4 styles_root__XwglO"
                                      data-tid="41068c56">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk" href="/film/435/box/">+
                                        ${'$'}150&nbsp;000&nbsp;000 = ${'$'}286&nbsp;801&nbsp;374</a><a href="/film/435/box/"
                                        class="styles_linkLight__cha3C styles_link__3QfAk">сборы</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Зрители
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="328581d6">
                                      <span class="styles_valueLight__nAaO3 styles_value__g6yP4 styles_item__qLVK1"
                                        data-tid="59164a46"><img src="https://st.kp.yandex.net/images/flags/flag-1.gif"
                                          alt="США" class="styles_icon__tVSsA">26 млн </span>, <span
                                        class="styles_valueLight__nAaO3 styles_value__g6yP4 styles_item__qLVK1"
                                        data-tid="59164a46"><img src="https://st.kp.yandex.net/images/flags/flag-3.gif"
                                          alt="Германия" class="styles_icon__tVSsA">2.1 млн </span>, <span
                                        class="styles_valueLight__nAaO3 styles_value__g6yP4 styles_item__qLVK1"
                                        data-tid="59164a46"><img src="https://st.kp.yandex.net/images/flags/flag-14.gif"
                                          alt="Италия" class="styles_icon__tVSsA">1.7 млн </span>, <a href="/film/435/dates/"
                                        class="styles_linkLight__cha3C styles_link__3QfAk">...</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Премьера в Росcии
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="ca30f216">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk" href="/premiere/ru/2000/to/435/#435"
                                        data-tid="3aaab4fd">18 апреля 2000</a>,&nbsp;<a
                                        class="styles_linkLight__cha3C styles_link__3QfAk" href="/lists/m_act[company]/8/"
                                        data-tid="3aaab4fd">«West»</a><span class="styles_stickers__hGaZH"></span>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Премьера в мире
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="d5ff4cc">
                                      <a class="styles_linkLight__cha3C styles_link__3QfAk" href="/film/435/dates/"
                                        data-tid="603f73a4">6 декабря 1999</a>, <a href="/film/435/dates/"
                                        class="styles_linkLight__cha3C styles_link__3QfAk">...</a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Релиз на DVD
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="e1e37c21">
                                      <div class="styles_valueLight__nAaO3 styles_value__g6yP4">
                                        13 февраля 2001, «West Video»
                                      </div>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Возраст
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4 styles_restrictionRow__JTXWD"
                                      data-tid="b7fd8541">
                                      <a class="styles_restrictionLink__iy4n9"><span
                                          class="styles_rootHighContrast__Bevle styles_rootHighContrastInDark___UcNT"
                                          data-tid="5c1ffa33">16+</span></a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Рейтинг MPAA
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4 styles_restrictionRow__JTXWD"
                                      data-tid="b7fd8541">
                                      <a class="styles_restrictionLink__iy4n9" href="/film/435/rn/R/"><span
                                          class="styles_rootHighContrast__Bevle styles_rootHighContrastInDark___UcNT"
                                          data-tid="5c1ffa33">R</span><span
                                          class="styles_restrictionDescription__4j5Pk styles_valueLight__nAaO3 styles_value__g6yP4">лицам
                                          до 17 лет обязательно присутствие взрослого</span></a>
                                    </div>
                                  </div>
                                  <div class="styles_rowLight__P8Y_1 styles_row__da_RK" data-tid="7cda04a5">
                                    <div class="styles_titleLight__HIbfT styles_title__b1HVo">
                                      Время
                                    </div>
                                    <div class="styles_valueLight__nAaO3 styles_value__g6yP4" data-tid="e1e37c21">
                                      <div class="styles_valueLight__nAaO3 styles_value__g6yP4">
                                        189 мин. / 03:09
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="styles_column__r2MWX styles_md_6__XDxd6 styles_lg_6__eGSDb" data-tid="893da4ad">
                                <div class="styles_mainSide__qVMH4">
                                  <div class="film-crew-block styles_filmCrew__tx5Wt" data-tid="f984424">
                                    <div class="styles_actors__wn_C4" data-tid="38ecf27e">
                                      <h3
                                        class="film-page-section-title styles_rootTitle__2g8Sk styles_title__RbMgF styles_rootXxsm__Jjccw styles_root__B8zR6 styles_rootLight__m9pzE">
                                        <a href="/film/435/cast/" class="styles_link__KtvyW" data-tid="6a319a9e">В главных
                                          ролях</a>
                                      </h3>
                                      <ul class="styles_list___ufg4">
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/9144/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Том Хэнкс</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/12759/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Дэвид Морс</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/22527/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Бонни Хант</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/677/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Майкл Кларк Дункан</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/20664/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Джеймс Кромуэлл</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/8989/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Майкл Джитер</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/1130/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Грэм Грин</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/12761/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Даг Хатчисон</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/21496/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Сэм Рокуэлл</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/7370/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Барри Пеппер</a></li>
                                      </ul><a href="/film/435/cast/"
                                        class="styles_moreItemsLink__hfZmk styles_moreItems__tlpNN">57 актеров</a>
                                    </div>
                                    <div data-tid="38ecf27e">
                                      <h3
                                        class="film-page-section-title styles_rootTitle__2g8Sk styles_title__RbMgF styles_rootXxsm__Jjccw styles_root__B8zR6 styles_rootLight__m9pzE">
                                        <a href="/film/435/cast/who_is/voice/" class="styles_link__KtvyW"
                                          data-tid="6a319a9e">Роли дублировали</a>
                                      </h3>
                                      <ul class="styles_list___ufg4">
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/1616407/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Всеволод Кузнецов</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/261729/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Владимир Антоник</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/269650/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Любовь Германова</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/231569/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Валентин Голубенко</a></li>
                                        <li class="styles_root__vKDSE styles_rootInDark__Nm7il" data-tid="2e6eb73e"><a
                                            href="/name/36411/" class="styles_link__Act80" itemprop="actor"
                                            data-tid="d4e8d214">Александр Белявский</a></li>
                                      </ul><a href="/film/435/cast/who_is/voice/"
                                        class="styles_moreItemsLink__hfZmk styles_moreItems__tlpNN">18 актеров</a>
                                    </div>
                                  </div>
                                  <div class="styles_root__b_uZM styles_awards__stpdy" data-tid="8986781d">
                                    <a href="/film/435/awards/" class="styles_link__J0S43 styles_linkOscar__ygbHO"
                                      data-tid="cbdd8e90"><img class="image styles_root__DZigd"
                                        src="//st.kp.yandex.net/images/movies/awardOscarGray.png" data-tid="d813cf42"><span
                                        class="styles_nominationCount__Lf_e1 styles_nominationCountDark___7jTe">4</span></a>
                                    <div class="styles_popover__08Mlf styles_root__SeBrp" data-tid="e30ff91d">
                                      <div class="styles_nominations__I5ywE">
                                        <h3
                                          class="film-page-section-title styles_rootTitle__2g8Sk styles_title__8qqmP styles_rootMd__7Q1_t styles_root__B8zR6 styles_rootDark__7yGTp">
                                          <a href="/film/435/awards/" class="styles_link__KtvyW" data-tid="6a319a9e">Оскар<span
                                              class="styles_moreItem__U5bzS">4</span></a>
                                        </h3>
                                        <ul class="styles_root__GOA2M" data-tid="ae580d90">
                                          <li><span class="styles_year__jgPZo">2000</span><span
                                              class="styles_listTitle__8bDJP">Номинации</span></li>
                                          <li data-tid="b4b29a33"><span class="styles_year__jgPZo"></span>
                                            <div>
                                              <div class="styles_nomination__kTCOI" data-tid="b4b29a33">
                                                <div class="styles_nominationTitle__dlmVi">
                                                  Лучший фильм
                                                </div>
                                              </div>
                                              <div class="styles_nomination__kTCOI" data-tid="b4b29a33">
                                                <div class="styles_nominationTitle__dlmVi">
                                                  Лучшая мужская роль второго плана
                                                </div>
                                              </div>
                                              <div class="styles_nomination__kTCOI" data-tid="b4b29a33">
                                                <div class="styles_nominationTitle__dlmVi">
                                                  Лучший адаптированный сценарий
                                                </div>
                                              </div>
                                              <div class="styles_nomination__kTCOI" data-tid="b4b29a33">
                                                <div class="styles_nominationTitle__dlmVi">
                                                  Лучший звук
                                                </div>
                                              </div>
                                            </div>
                                          </li>
                                        </ul><a href="/film/435/awards/" class="styles_allAwardsLink__6vgYm"
                                          data-tid="935823d">Все награды</a>
                                      </div>
                                      <div class="styles_mainAward__Riz_k">
                                        <a href="/film/435/awards/" class="styles_link__J0S43 styles_linkOscar__ygbHO"
                                          data-tid="cbdd8e90"><img class="image styles_root__DZigd"
                                            src="//st.kp.yandex.net/images/movies/awardOscarGray.png" data-tid="d813cf42"><span
                                            class="styles_nominationCount__Lf_e1">4</span></a>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="styles_root__2kxYy" data-tid="914bd01c">
                              <div class="styles_column__r2MWX styles_md_16__PEQm2 styles_lg_20__JnV5e" data-tid="893da4ad">
                              </div>
                            </div>
                            <div class="styles_root__2kxYy styles_topLine__xigow styles_topLineUserNote__hxKqF"
                              data-tid="914bd01c">
                              <div class="styles_column__r2MWX styles_md_11__UdIH_ styles_lg_15__Ai53P" data-tid="893da4ad">
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="styles_root__B1q5W styles_rootLight___QD_Q styles_root__axj8R" data-tid="21855542">
                  <div class="styles_root__UtArQ" data-tid="3716659c">
                    <div class="styles_root__2kxYy" data-tid="914bd01c">
                      <div class="styles_column__r2MWX styles_md_16__PEQm2 styles_lg_20__JnV5e styles_column__5dEFP"
                        data-tid="893da4ad">
                        <div class="styles_main__vjk2Q styles_additionalInformationSection__GmCD7">
                          <div class="styles_root__KtmEB" data-tid="37b61dba">
                            <div data-tid="e0411e82">
                              <div class="" style="min-width:1px" data-tid="517927c6"></div>
                            </div>
                          </div><span id="film-details-info" style="height:0" data-tid="32753834"></span>
                          <div style="min-width:1px" data-tid="517927c6">
                            <div data-tid="e0411e82">
                              <div class="film-details-block styles_root__FF738" data-tid="71e757c">
                                <div class="styles_tabsSection__aKq4_">
                                  <div class="styles_root__rVp2r" data-tid="3855337e">
                                    <div class="styles_itemsSpoiler__ROHvQ styles_ssr__ytsd7" data-tid="de3d23c9">
                                      <div class="styles_itemContainerWrap__7O8FL" data-tid="7232869f">
                                        <div class="styles_root__WrVXN styles_itemActive__Nd9PE styles_item__CGufh"
                                          data-tid="b92ae11f">
                                          <span class="styles_itemActive__S4PQM styles_item__wPOY6">Обзор</span>
                                        </div>
                                      </div>
                                      <div class="styles_itemContainerWrap__7O8FL" data-tid="7232869f">
                                        <div class="styles_root__WrVXN styles_item__CGufh" data-tid="b92ae11f">
                                          <a class="styles_itemDefault__nPthf styles_item__wPOY6"
                                            href="/film/435/awards/">Награды</a>
                                        </div>
                                      </div>
                                      <div class="styles_itemContainerWrap__7O8FL" data-tid="7232869f">
                                        <div class="styles_root__WrVXN styles_item__CGufh" data-tid="b92ae11f">
                                          <a class="styles_itemDefault__nPthf styles_item__wPOY6"
                                            href="/film/435/dates/">Премьеры</a>
                                        </div>
                                      </div>
                                      <div class="styles_itemContainerWrap__7O8FL" data-tid="7232869f">
                                        <div class="styles_root__WrVXN styles_item__CGufh" data-tid="b92ae11f">
                                          <a class="styles_itemDefault__nPthf styles_item__wPOY6"
                                            href="/film/435/stills/">Изображения</a>
                                        </div>
                                      </div>
                                      <div class="styles_itemContainerWrap__7O8FL" data-tid="7232869f">
                                        <div class="styles_root__WrVXN styles_item__CGufh" data-tid="b92ae11f">
                                          <a class="styles_itemDefault__nPthf styles_item__wPOY6"
                                            href="/film/435/video/">Трейлеры</a>
                                        </div>
                                      </div>
                                      <div class="styles_itemContainerWrap__7O8FL" data-tid="7232869f">
                                        <div class="styles_root__WrVXN styles_item__CGufh" data-tid="b92ae11f">
                                          <a class="styles_itemDefault__nPthf styles_item__wPOY6"
                                            href="/film/435/studio/">Студии</a>
                                        </div>
                                      </div>
                                      <div class="styles_itemContainerWrap__7O8FL" data-tid="7232869f">
                                        <div class="styles_root__WrVXN styles_item__CGufh" data-tid="b92ae11f">
                                          <a class="styles_itemDefault__nPthf styles_item__wPOY6"
                                            href="/film/435/other/">Связи</a>
                                        </div>
                                      </div>
                                      <div class="styles_itemContainerWrap__7O8FL" data-tid="7232869f">
                                        <div class="styles_root__WrVXN styles_item__CGufh" data-tid="b92ae11f">
                                          <a class="styles_itemDefault__nPthf styles_item__wPOY6"
                                            href="/film/435/reviews/">Рецензии</a>
                                        </div>
                                      </div>
                                      <div class="styles_itemContainerWrap__7O8FL" data-tid="7232869f">
                                        <div class="styles_root__WrVXN styles_item__CGufh" data-tid="b92ae11f">
                                          <span class="styles_itemDefault__nPthf styles_item__wPOY6">Еще...</span>
                                          <div class="styles_dropDownContainer__g27mF">
                                            <div class="styles_dropDown__QqORT styles_dropDown__wMbnB">
                                              <div class="styles_root__WrVXN styles_subItem__IAR5h styles_item__CGufh"
                                                data-tid="b92ae11f">
                                                <a class="styles_itemDefault__nPthf styles_item__wPOY6"
                                                  href="/film/435/sites/">Сайты</a>
                                              </div>
                                              <div class="styles_root__WrVXN styles_subItem__IAR5h styles_item__CGufh"
                                                data-tid="b92ae11f">
                                                <a class="styles_itemDefault__nPthf styles_item__wPOY6"
                                                  href="/film/435/tracks/">Саундтреки</a>
                                              </div>
                                              <div class="styles_root__WrVXN styles_subItem__IAR5h styles_item__CGufh"
                                                data-tid="b92ae11f">
                                                <a class="styles_itemDefault__nPthf styles_item__wPOY6"
                                                  href="/film/435/subscribe/">Подписка на обновления</a>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                                <div class="styles_synopsisSection__nJoAj">
                                  <div class="styles_filmSynopsis__Cu2Oz" data-tid="7f916518">
                                    <p class="styles_paragraph__wEGPz" data-tid="bbb11238">Пол Эджкомб&nbsp;—&nbsp;начальник
                                      блока смертников в&nbsp;тюрьме «Холодная гора», каждый из&nbsp;узников которого однажды
                                      проходит «зеленую милю» по&nbsp;пути к&nbsp;месту казни. Пол&nbsp;повидал много
                                      заключённых и&nbsp;надзирателей за&nbsp;время работы. Однако гигант Джон Коффи, обвинённый
                                      в&nbsp;страшном преступлении, стал одним из&nbsp;самых необычных обитателей блока.</p>
                                  </div>
                                </div>
                                <div class="styles_filmRatingSection___Sph9">
                                  <div class="" data-tid="af0b971c">
                                    <h3
                                      class="film-page-section-title styles_rootTitle__2g8Sk styles_title__vtVG_ styles_rootMd__7Q1_t styles_root__B8zR6 styles_rootDark__7yGTp">
                                      Рейтинг фильма</h3>
                                    <div class="styles_content__QYDgA">
                                      <div class="styles_formContainer__yBeUw">
                                        <form class="styles_form__2pcvP styles_form__Bw8gI film-rate-form" data-tid="ad886728">
                                          <label class="styles_radio__IqZef styles_root__FBsKv styles_rootFilledNumber__aUL_f"
                                            data-value="1" data-tid="5d365105"><input type="radio" class="styles_input__SRM0B"
                                              name="star" value="1"><span class="styles_iconContainer__9nPOy"><span
                                                class="styles_icon__snCTV styles_defaultIcon__8fqbF styles_baseIcon__MNyDV styles_filledIcon__gob6k styles_baseIcon__MNyDV"
                                                data-tid="5a15c5d7"></span></span></label><label
                                            class="styles_radio__IqZef styles_root__FBsKv styles_rootFilledNumber__aUL_f"
                                            data-value="2" data-tid="5d365105"><input type="radio" class="styles_input__SRM0B"
                                              name="star" value="2"><span class="styles_iconContainer__9nPOy"><span
                                                class="styles_icon__snCTV styles_defaultIcon__8fqbF styles_baseIcon__MNyDV styles_filledIcon__gob6k styles_baseIcon__MNyDV"
                                                data-tid="5a15c5d7"></span></span></label><label
                                            class="styles_radio__IqZef styles_root__FBsKv styles_rootFilledNumber__aUL_f"
                                            data-value="3" data-tid="5d365105"><input type="radio" class="styles_input__SRM0B"
                                              name="star" value="3"><span class="styles_iconContainer__9nPOy"><span
                                                class="styles_icon__snCTV styles_defaultIcon__8fqbF styles_baseIcon__MNyDV styles_filledIcon__gob6k styles_baseIcon__MNyDV"
                                                data-tid="5a15c5d7"></span></span></label><label
                                            class="styles_radio__IqZef styles_root__FBsKv styles_rootFilledNumber__aUL_f"
                                            data-value="4" data-tid="5d365105"><input type="radio" class="styles_input__SRM0B"
                                              name="star" value="4"><span class="styles_iconContainer__9nPOy"><span
                                                class="styles_icon__snCTV styles_defaultIcon__8fqbF styles_baseIcon__MNyDV styles_filledIcon__gob6k styles_baseIcon__MNyDV"
                                                data-tid="5a15c5d7"></span></span></label><label
                                            class="styles_radio__IqZef styles_root__FBsKv styles_rootFilledNumber__aUL_f"
                                            data-value="5" data-tid="5d365105"><input type="radio" class="styles_input__SRM0B"
                                              name="star" value="5"><span class="styles_iconContainer__9nPOy"><span
                                                class="styles_icon__snCTV styles_defaultIcon__8fqbF styles_baseIcon__MNyDV styles_filledIcon__gob6k styles_baseIcon__MNyDV"
                                                data-tid="5a15c5d7"></span></span></label><label
                                            class="styles_radio__IqZef styles_root__FBsKv styles_rootFilledNumber__aUL_f"
                                            data-value="6" data-tid="5d365105"><input type="radio" class="styles_input__SRM0B"
                                              name="star" value="6"><span class="styles_iconContainer__9nPOy"><span
                                                class="styles_icon__snCTV styles_defaultIcon__8fqbF styles_baseIcon__MNyDV styles_filledIcon__gob6k styles_baseIcon__MNyDV"
                                                data-tid="5a15c5d7"></span></span></label><label
                                            class="styles_radio__IqZef styles_root__FBsKv styles_rootFilledNumber__aUL_f"
                                            data-value="7" data-tid="5d365105"><input type="radio" class="styles_input__SRM0B"
                                              name="star" value="7"><span class="styles_iconContainer__9nPOy"><span
                                                class="styles_icon__snCTV styles_defaultIcon__8fqbF styles_baseIcon__MNyDV styles_filledIcon__gob6k styles_baseIcon__MNyDV"
                                                data-tid="5a15c5d7"></span></span></label><label
                                            class="styles_radio__IqZef styles_root__FBsKv styles_rootFilledNumber__aUL_f"
                                            data-value="8" data-tid="5d365105"><input type="radio" class="styles_input__SRM0B"
                                              name="star" value="8"><span class="styles_iconContainer__9nPOy"><span
                                                class="styles_icon__snCTV styles_defaultIcon__8fqbF styles_baseIcon__MNyDV styles_filledIcon__gob6k styles_baseIcon__MNyDV"
                                                data-tid="5a15c5d7"></span></span></label><label
                                            class="styles_radio__IqZef styles_root__FBsKv styles_rootFilledNumber__aUL_f"
                                            data-value="9" data-tid="5d365105"><input type="radio" class="styles_input__SRM0B"
                                              name="star" value="9"><span class="styles_iconContainer__9nPOy"><span
                                                class="styles_icon__snCTV styles_defaultIcon__8fqbF styles_baseIcon__MNyDV styles_filledIcon__gob6k styles_baseIcon__MNyDV"
                                                data-tid="5a15c5d7"></span></span></label><label
                                            class="styles_radio__IqZef styles_root__FBsKv" data-value="10"
                                            data-tid="5d365105"><input type="radio" class="styles_input__SRM0B" name="star"
                                              value="10"><span class="styles_iconContainer__9nPOy"><span
                                                class="styles_icon__snCTV styles_defaultIcon__8fqbF styles_baseIcon__MNyDV"
                                                data-tid="5a15c5d7"></span><span style="width:6%"
                                                class="styles_partialIcon__PpsMw styles_defaultIcon__8fqbF styles_baseIcon__MNyDV styles_filledIcon__gob6k styles_baseIcon__MNyDV"
                                                data-tid="5a15c5d7"></span></span></label>
                                        </form>
                                        <div class="styles_buttons__dSk8l">
                                          <button
                                            class="styles_scrollToAddReviewButton__GAfqB styles_root__sjOi_ styles_rootLight__zgQRd styles_rootWithTitle__F2vRG styles_root__lbhjd styles_rootLight__4o4CJ styles_rootGhost__7yeKn"><span
                                              class="styles_tooltip__BZYE_" data-tid="d43912a6">Написать рецензию</span>Написать
                                            рецензию</button>
                                        </div>
                                      </div>
                                      <div class="styles_ratingContainer__RhJ96">
                                        <div>
                                          <div
                                            class="film-rating styles_root__7rVf_ styles_rootLSize__X4aDt styles_rootInLight__4w53g"
                                            data-tid="71598065">
                                            <div class="styles_ratingValue__UO6Zl styles_rootLSize__X4aDt">
                                              <div class="styles_valueBlock___nWKb">
                                                <span class="styles_value__N2Vzt"><a
                                                    class="film-rating-value styles_rootKpTop__pByhB styles_rootLink__mm0kW"
                                                    href="/film/435/votes/">9.1</a></span><a
                                                  class="styles_topListPositionBadge__j5rPp styles_root__jYb9p styles_rootLegacy__Ft6DV"
                                                  href="/lists/movies/top250/" data-tid="45e77845">
                                                  <div class="styles_root__ixhBE styles_icon__fFV9t styles_size36x37__A0Apc"
                                                    data-tid="a757238c"></div>
                                                  <div>
                                                    <div class="styles_content__yUcEK">
                                                      <span class="styles_root__Q2THk styles_title__b0LlO"
                                                        data-tid="b012c88c">топ 250</span><span
                                                        class="styles_root__Q2THk styles_position__pm10U" data-tid="b012c88c">#
                                                        <!-- -->1
                                                      </span>
                                                    </div><span class="styles_root__Q2THk styles_subtitle__9DKd0"
                                                      data-tid="b012c88c">Кинопоиск</span>
                                                  </div>
                                                </a>
                                              </div>
                                              <div class="styles_countBlock__jxRDI">
                                                <span class="styles_count__iOIwD">762 061 оценка</span><span
                                                  class="styles_count__iOIwD">
                                                  <div class="film-sub-rating" data-tid="3d4f49c8">
                                                    <span class="styles_valueSection__0Tcsy">IMDb
                                                      <!-- -->:
                                                      <!-- -->8.60
                                                    </span><span class="styles_count__89cAz">1 253 858 оценок</span>
                                                  </div>
                                                </span>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                                <div class="styles_criticRatingSection__rlcU9">
                                  <div class="styles_root__i6At7" data-tid="f914d65f">
                                    <div class="styles_ratingBar__W5otN" data-tid="9f39fdf1">
                                      <h3
                                        class="film-page-section-title styles_rootTitle__2g8Sk styles_header__I8hG1 styles_rootSm__r1lYg styles_root__B8zR6 styles_rootDark__7yGTp">
                                        Рейтинг кинокритиков в мире</h3>
                                      <div class="styles_filmRatingBar__Mks7X styles_ratingBar__NSzsB styles_withValue__PEUCo"
                                        data-tid="d610b8e8">
                                        <div class="styles_greenBar__NAQmT styles_bar__7hk5H" style="flex:79;min-width:30px">
                                          108
                                        </div>
                                        <div class="styles_redBar__b_rlR styles_bar__7hk5H" style="flex:21;min-width:30px">
                                          29
                                        </div>
                                      </div>
                                      <div class="styles_actionBar__6SGOg">
                                        <div class="styles_actionBarLeft__rwQsY">
                                          <div
                                            class="film-rating styles_root__7rVf_ styles_rootSSize__sQJqB styles_rootInLight__4w53g"
                                            data-tid="71598065">
                                            <div class="styles_ratingValue__UO6Zl styles_rootSSize__sQJqB">
                                              <div class="styles_valueBlock___nWKb">
                                                <span class="styles_value__N2Vzt"><span
                                                    class="film-rating-value styles_rootPositive__mLBSO">79%</span></span>
                                              </div>
                                              <div class="styles_countBlock__jxRDI">
                                                <span class="styles_count__iOIwD">137 оценок<span
                                                    class="styles_starValue__tchEE">6.8</span></span>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                    <div class="styles_ratingBar__W5otN" data-tid="9f39fdf1">
                                      <h3
                                        class="film-page-section-title styles_rootTitle__2g8Sk styles_header__I8hG1 styles_rootSm__r1lYg styles_root__B8zR6 styles_rootDark__7yGTp">
                                        <a href="/film/435/press/" class="styles_link__KtvyW" data-tid="6a319a9e">В России</a>
                                      </h3>
                                      <div class="styles_filmRatingBar__Mks7X styles_ratingBar__NSzsB styles_withValue__PEUCo"
                                        data-tid="d610b8e8">
                                        <div class="styles_greenBar__NAQmT styles_bar__7hk5H" style="flex:0"></div>
                                        <div class="styles_redBar__b_rlR styles_bar__7hk5H" style="flex:100;min-width:30px">
                                          1
                                        </div>
                                      </div>
                                      <div class="styles_actionBar__6SGOg">
                                        <div class="styles_actionBarLeft__rwQsY">
                                          <div
                                            class="film-rating styles_root__7rVf_ styles_rootSSize__sQJqB styles_rootEmptyValue___oY43 styles_rootInLight__4w53g"
                                            data-tid="71598065">
                                            <div class="styles_ratingValue__UO6Zl styles_rootSSize__sQJqB">
                                              <div class="styles_valueBlock___nWKb">
                                                <span class="styles_value__N2Vzt"><span
                                                    class="film-rating-value styles_rootUnknown__cvNn1">–</span></span>
                                              </div>
                                              <div class="styles_countBlock__jxRDI">
                                                <span class="styles_count__iOIwD">1 оценка</span>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div><span id="film-similar_movies" style="height:0" data-tid="32753834"></span>
                          <div style="min-width:1px" data-tid="517927c6">
                            <div class="styles_root__AphAt" data-tid="718c3e1">
                              <span class="styles_title__WdckK styles_root__llf0m styles_rootMd__Mp2De styles_root__2Vgyb"
                                data-tid="ad14a6be">‌</span>
                              <div class="styles_carousel__TDvSy">
                                <div class="styles_card__oVZiZ" data-tid="f87b3509">
                                  <span class="styles_poster__EfxdH styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardTitle__c0Qni styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardSubtitle__eJwHH styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                </div>
                                <div class="styles_card__oVZiZ" data-tid="f87b3509">
                                  <span class="styles_poster__EfxdH styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardTitle__c0Qni styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardSubtitle__eJwHH styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                </div>
                                <div class="styles_card__oVZiZ" data-tid="f87b3509">
                                  <span class="styles_poster__EfxdH styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardTitle__c0Qni styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardSubtitle__eJwHH styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                </div>
                                <div class="styles_card__oVZiZ" data-tid="f87b3509">
                                  <span class="styles_poster__EfxdH styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardTitle__c0Qni styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardSubtitle__eJwHH styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                </div>
                                <div class="styles_card__oVZiZ" data-tid="f87b3509">
                                  <span class="styles_poster__EfxdH styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardTitle__c0Qni styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardSubtitle__eJwHH styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                </div>
                              </div>
                            </div>
                          </div><span id="film-trailers" style="height:0" data-tid="32753834"></span>
                          <div style="min-width:1px" data-tid="517927c6">
                            <div class="styles_root__NaGgU" data-tid="388eb013">
                              <span class="styles_title__DqgWT styles_root__llf0m styles_rootMd__Mp2De styles_root__2Vgyb"
                                data-tid="ad14a6be">‌</span>
                              <div class="styles_root__2kxYy" data-tid="914bd01c">
                                <div class="styles_column__r2MWX styles_md_8__YNPjM styles_lg_10__hutg7" data-tid="893da4ad">
                                  <div class="" data-tid="2e582843">
                                    <span class="styles_preview__ruOp9 styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                      class="styles_title__vd96O styles_title__dm9vQ styles_root__2Vgyb"
                                      data-tid="ad14a6be">‌</span><span
                                      class="styles_date__d5xwh styles_date__X6slX styles_root__2Vgyb"
                                      data-tid="ad14a6be">‌</span>
                                  </div>
                                </div>
                                <div class="styles_column__r2MWX styles_md_8__YNPjM styles_lg_10__hutg7" data-tid="893da4ad">
                                  <div class="" data-tid="2e582843">
                                    <span class="styles_preview__ruOp9 styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                      class="styles_title__vd96O styles_title__dm9vQ styles_root__2Vgyb"
                                      data-tid="ad14a6be">‌</span><span
                                      class="styles_date__d5xwh styles_date__X6slX styles_root__2Vgyb"
                                      data-tid="ad14a6be">‌</span>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div><span id="film-facts" style="height:0" data-tid="32753834"></span>
                          <div style="min-width:1px" data-tid="517927c6">
                            <div class="styles_root__DA3xg" data-tid="f66307a6">
                              <span class="styles_title__juLbV styles_root__llf0m styles_rootMd__Mp2De styles_root__2Vgyb"
                                data-tid="ad14a6be">‌</span><span class="styles_trivias__dYVWd styles_root__2Vgyb"
                                data-tid="ad14a6be">‌</span>
                            </div>
                          </div><span id="film-bloopers" style="height:0" data-tid="32753834"></span>
                          <div style="min-width:1px" data-tid="517927c6">
                            <div class="styles_root__DA3xg" data-tid="f66307a6">
                              <span class="styles_title__juLbV styles_root__llf0m styles_rootMd__Mp2De styles_root__2Vgyb"
                                data-tid="ad14a6be">‌</span><span class="styles_trivias__dYVWd styles_root__2Vgyb"
                                data-tid="ad14a6be">‌</span>
                            </div>
                          </div><span id="film-media-posts" style="height:0" data-tid="32753834"></span>
                          <div style="min-width:1px" data-tid="517927c6">
                            <div class="styles_root__5UEhB" data-tid="bb544b3">
                              <span class="styles_title__G7P6B styles_root__llf0m styles_rootMd__Mp2De styles_root__2Vgyb"
                                data-tid="ad14a6be">‌</span>
                              <div class="styles_carousel__a_hbM">
                                <div class="styles_root__zc9LM styles_item__XpypV" data-tid="70f4c18f">
                                  <span class="styles_image__yrRqu styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                  <div class="styles_content__mbyG4">
                                    <span class="styles_title__c_Mad styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                      class="styles_subtitle__1U4oC styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                      class="styles_date__RRcaK styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                  </div>
                                </div>
                                <div class="styles_root__zc9LM styles_item__XpypV" data-tid="70f4c18f">
                                  <span class="styles_image__yrRqu styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                  <div class="styles_content__mbyG4">
                                    <span class="styles_title__c_Mad styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                      class="styles_subtitle__1U4oC styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                      class="styles_date__RRcaK styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                  </div>
                                </div>
                                <div class="styles_root__zc9LM styles_item__XpypV" data-tid="70f4c18f">
                                  <span class="styles_image__yrRqu styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                  <div class="styles_content__mbyG4">
                                    <span class="styles_title__c_Mad styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                      class="styles_subtitle__1U4oC styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                      class="styles_date__RRcaK styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div><span id="today-in-cinema-block" style="height:0" data-tid="32753834"></span>
                          <div style="min-width:1px" data-tid="517927c6">
                            <div class="styles_root__AphAt" data-tid="718c3e1">
                              <span class="styles_title__WdckK styles_root__llf0m styles_rootMd__Mp2De styles_root__2Vgyb"
                                data-tid="ad14a6be">‌</span>
                              <div class="styles_carousel__TDvSy">
                                <div class="styles_card__oVZiZ" data-tid="f87b3509">
                                  <span class="styles_poster__EfxdH styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardTitle__c0Qni styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardSubtitle__eJwHH styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                </div>
                                <div class="styles_card__oVZiZ" data-tid="f87b3509">
                                  <span class="styles_poster__EfxdH styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardTitle__c0Qni styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardSubtitle__eJwHH styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                </div>
                                <div class="styles_card__oVZiZ" data-tid="f87b3509">
                                  <span class="styles_poster__EfxdH styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardTitle__c0Qni styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardSubtitle__eJwHH styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                </div>
                                <div class="styles_card__oVZiZ" data-tid="f87b3509">
                                  <span class="styles_poster__EfxdH styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardTitle__c0Qni styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardSubtitle__eJwHH styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                </div>
                                <div class="styles_card__oVZiZ" data-tid="f87b3509">
                                  <span class="styles_poster__EfxdH styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardTitle__c0Qni styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_cardSubtitle__eJwHH styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                </div>
                              </div>
                            </div>
                          </div><span id="film-critic-reviews" style="height:0" data-tid="32753834"></span>
                          <div style="min-width:1px" data-tid="517927c6">
                            <div class="styles_root__IDbib" data-tid="590d7889">
                              <span class="styles_title__1_WZz styles_root__llf0m styles_rootMd__Mp2De styles_root__2Vgyb"
                                data-tid="ad14a6be">‌</span>
                              <div class="styles_carousel__pAWGW">
                                <span class="styles_item__8JpPx styles_root__9s9NS styles_root__2Vgyb"
                                  data-tid="ad14a6be">‌</span><span
                                  class="styles_item__8JpPx styles_root__9s9NS styles_root__2Vgyb"
                                  data-tid="ad14a6be">‌</span><span
                                  class="styles_item__8JpPx styles_root__9s9NS styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                              </div>
                            </div>
                          </div>
                          <div id="user-reviews" class="styles_root__bpW7N" data-tid="7a20dd1e">
                            <div data-tid="e0411e82">
                              <span id="film-users-reviews" style="height:0" data-tid="32753834"></span>
                              <div style="min-width:1px" data-tid="517927c6">
                                <div class="styles_root__rly1E" data-tid="a4f2984f">
                                  <span class="styles_title__4zZWY styles_root__llf0m styles_rootLg__v_8HV styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span><span class="styles_button__hH7LK styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span><span class="styles_review__qp_BA styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="styles_delimiter__m7GQp styles_delimiterLight__tPYdT"></div>
                      <div
                        class="styles_column__r2MWX styles_md_6__XDxd6 styles_lg_8__7Mdim styles_column__5dEFP styles_sidebarSection__q_p1Y"
                        data-tid="893da4ad">
                        <div class="styles_sidebar__mZOfP">
                          <div class="" data-tid="43d5b8ef">
                            <div class="" data-tid="e810e001">
                              <div id="foxadbanner_200_200_film" class="styles_container__XXCpX" data-tid="9501d3f4"></div>
                              <script
                                nonce="yKPyXX1EXRV3Cjyu+xPE5g==">try { window.adfoxService.renderBanner('foxadbanner_200_200_film', JSON.parse('{"containerId":"foxadbanner_200_200_film","ownerId":251518,"params":{"p1":"cmdke","p2":"gyew","puid10":"0","puid11":"0"},"cspNonce":"yKPyXX1EXRV3Cjyu+xPE5g=="}')); } catch (e) { console.error(e) }</script>
                            </div>
                          </div>
                          <div class="styles_root____5P5" data-tid="14985c6b">
                            <div class="styles_header__iErfu">
                              <nav class="styles_nav__gHl7k">
                                <button tabindex="0" type="button" class="styles_root__5QqzE styles_rootSelected__oVvef"
                                  data-tid="1f6a4ca6">Друзья</button>
                              </nav>
                              <div class="styles_rating__sV8yH"></div>
                            </div>
                            <div class="styles_root__sGbD8" data-tid="c17d21c6">
                              <button tabindex="0" type="button" class="styles_link__xVM_W">Найдите друзей</button>,
                              зарегистрированных на Кинопоиске, и здесь появятся оценки, которые ваши друзья поставили этому
                              фильму...
                            </div>
                          </div><span id="movie-lists-relations" style="height:0" data-tid="32753834"></span>
                          <div style="min-width:1px" data-tid="517927c6">
                            <div class="styles_root__UkkRY" data-tid="d34ff620">
                              <span
                                class="styles_skeletonTitle__zDdnJ styles_title__zJKfl styles_root__llf0m styles_rootSm__Nt66I styles_root__2Vgyb"
                                data-tid="ad14a6be">‌</span>
                              <div class="styles_root__8XJ8A styles_item__XMMSz" data-tid="4b01f8b7">
                                <div class="styles_img__dgPpf styles_img__SQ08u"></div>
                                <div class="styles_titleWrapper__Ik_kf">
                                  <span class="styles_title__4qO8I styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_subtitle__A8D9D styles_subtitle__qdb5g styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span>
                                </div>
                              </div>
                              <div class="styles_root__8XJ8A styles_item__XMMSz" data-tid="4b01f8b7">
                                <div class="styles_img__dgPpf styles_img__SQ08u"></div>
                                <div class="styles_titleWrapper__Ik_kf">
                                  <span class="styles_title__4qO8I styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_subtitle__A8D9D styles_subtitle__qdb5g styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span>
                                </div>
                              </div>
                              <div class="styles_root__8XJ8A styles_item__XMMSz" data-tid="4b01f8b7">
                                <div class="styles_img__dgPpf styles_img__SQ08u"></div>
                                <div class="styles_titleWrapper__Ik_kf">
                                  <span class="styles_title__4qO8I styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_subtitle__A8D9D styles_subtitle__qdb5g styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span>
                                </div>
                              </div>
                              <div class="styles_root__8XJ8A styles_item__XMMSz" data-tid="4b01f8b7">
                                <div class="styles_img__dgPpf styles_img__SQ08u"></div>
                                <div class="styles_titleWrapper__Ik_kf">
                                  <span class="styles_title__4qO8I styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_subtitle__A8D9D styles_subtitle__qdb5g styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span>
                                </div>
                              </div>
                              <div class="styles_root__8XJ8A styles_item__XMMSz" data-tid="4b01f8b7">
                                <div class="styles_img__dgPpf styles_img__SQ08u"></div>
                                <div class="styles_titleWrapper__Ik_kf">
                                  <span class="styles_title__4qO8I styles_root__2Vgyb" data-tid="ad14a6be">‌</span><span
                                    class="styles_subtitle__A8D9D styles_subtitle__qdb5g styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span>
                                </div>
                              </div>
                            </div>
                          </div><span id="soundtrack-section" style="height:0" data-tid="32753834"></span>
                          <div style="min-width:1px" data-tid="517927c6">
                            <div data-tid="373566cd">
                              <span
                                class="styles_skeletonTitle__uDC2G styles_root__llf0m styles_rootMd__Mp2De styles_root__2Vgyb"
                                data-tid="ad14a6be">‌</span>
                              <div class="styles_skeletonIframe__bRjU0">
                                <div class="styles_skeletonCoverWrap__HoP_s">
                                  <span class="styles_skeletonPicture__aal6r styles_root__2Vgyb" data-tid="ad14a6be">‌</span>
                                </div>
                                <div class="styles_skeletonContent__sfMYz">
                                  <span class="styles_skeletonAlbumTitle__ZYn6S styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span><span class="styles_skeletonSubtitle__wUNb_ styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span><span
                                    class="styles_skeletonPlayButton___gNe9 styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span><span class="styles_skeletonSong__m2xee styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span><span class="styles_skeletonSong__m2xee styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span><span class="styles_skeletonSong__m2xee styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span><span class="styles_skeletonSong__m2xee styles_root__2Vgyb"
                                    data-tid="ad14a6be">‌</span>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="styles_sticky__S5Rjy" data-tid="5da1a3ef">
                            <div class="styles_root__TryBS">
                              <div class="" style="min-width:1px" data-tid="517927c6"></div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="styles_footerContainer__Mk60T styles_baseContainer__8XBMw">
                <footer class="footer styles_rootDark__mtmaQ styles_root__sUtn2" data-tid="995d0553">
                  <section class="styles_root__kcCHj social-icons styles_socialMenu__gLaiH" data-tid="39df70f">
                    <a class="styles_icon__cHky_" href="https://vk.com/kinopoisk" target="_blank" rel="noopener noreferrer"
                      aria-label="presentation" data-tid="2e9b873"><img
                        src="//avatars.mds.yandex.net/get-bunker/118781/0ae3d1ca27d3794204beec7d3810025f8c2b7e87/svg"
                        alt="https://vk.com/kinopoisk" loading="lazy" data-tid="9107e4a2"></a><a class="styles_icon__cHky_"
                      href="https://www.facebook.com/kinopoisk" target="_blank" rel="noopener noreferrer"
                      aria-label="presentation" data-tid="2e9b873"><img
                        src="//avatars.mds.yandex.net/get-bunker/56833/0baf23635975a9f1b481833f37653aa2efceb3a1/svg"
                        alt="https://www.facebook.com/kinopoisk" loading="lazy" data-tid="9107e4a2"></a><a
                      class="styles_icon__cHky_" href="https://twitter.com/kinopoiskru" target="_blank"
                      rel="noopener noreferrer" aria-label="presentation" data-tid="2e9b873"><img
                        src="//avatars.mds.yandex.net/get-bunker/61205/97123f0bc0c689932a2fb6b62d3ab8ce04d7e936/svg"
                        alt="https://twitter.com/kinopoiskru" loading="lazy" data-tid="9107e4a2"></a><a
                      class="styles_icon__cHky_" href="https://telegram.me/kinopoisk" target="_blank" rel="noopener noreferrer"
                      aria-label="presentation" data-tid="2e9b873"><img
                        src="//avatars.mds.yandex.net/get-bunker/56833/9f570502e378d5e28a5a173a273fa811c4490a73/svg"
                        alt="https://telegram.me/kinopoisk" loading="lazy" data-tid="9107e4a2"></a><a class="styles_icon__cHky_"
                      href="https://www.instagram.com/kinopoisk/" target="_blank" rel="noopener noreferrer"
                      aria-label="presentation" data-tid="2e9b873"><img
                        src="//avatars.mds.yandex.net/get-bunker/50064/c6b1a28b4bf580d4cf96ec7f262aace67a4dde2e/svg"
                        alt="https://www.instagram.com/kinopoisk/" loading="lazy" data-tid="9107e4a2"></a><a
                      class="styles_icon__cHky_" href="https://www.youtube.com/user/kinopoisk" target="_blank"
                      rel="noopener noreferrer" aria-label="presentation" data-tid="2e9b873"><img
                        src="//avatars.mds.yandex.net/get-bunker/128809/65fe1abdd405eb82aec7490588a1ec6745d9ab87/svg"
                        alt="https://www.youtube.com/user/kinopoisk" loading="lazy" data-tid="9107e4a2"></a>
                  </section>
                  <ul class="footer__content-links styles_contentMenu__OgjQP">
                    <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                        href="https://yandex.ru/jobs/vacancies/dev/?from=kinopoisk&amp;services=kinopoisk" target="_blank"
                        rel="noopener noreferrer" class="footer__content-link styles_contentLink__mRKj9"
                        data-tid="2e9b873">Вакансии</a></li>
                    <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                        href="https://yandex.ru/adv/products/display/kinopoiskmedia" target="_blank" rel="noopener noreferrer"
                        class="footer__content-link styles_contentLink__mRKj9" data-tid="2e9b873">Реклама</a></li>
                    <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a href="/docs/usage/"
                        target="_blank" rel="noopener noreferrer" class="footer__content-link styles_contentLink__mRKj9"
                        data-tid="2e9b873">Соглашение</a></li>
                    <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                        href="https://yandex.ru/support/kinopoisk/index.html" target="_blank" rel="noopener noreferrer"
                        class="footer__content-link styles_contentLink__mRKj9" data-tid="2e9b873">Справка</a></li>
                    <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                        href="/media/rubric/19/" target="_blank" rel="noopener noreferrer"
                        class="footer__content-link styles_contentLink__mRKj9" data-tid="2e9b873">Блог</a></li>
                    <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                        href="https://www.surveygizmo.eu/s3/90259271/" target="_blank" rel="noopener noreferrer"
                        class="footer__content-link styles_contentLink__mRKj9" data-tid="2e9b873">Участие в исследованиях</a>
                    </li>
                    <li class="footer__content-item styles_contentMenuItem__yTKp2" data-tid="99325639"><a
                        href="https://kinopoisk.userecho.com/" target="_blank" rel="noopener noreferrer"
                        class="footer__content-link styles_contentLink__mRKj9" data-tid="2e9b873">Предложения</a></li>
                    <li class="footer__content-item styles_contentMenuItem__yTKp2"><button type="button"
                        class="styles_contentButton__Yfvdh">Служба поддержки</button></li>
                  </ul>
                  <div class="styles_root__a_qyh styles_mobileAppsMenu__E1mGj styles_rootDark__ZR7rh" data-tid="358cef48">
                    <a class="styles_store__JFbwQ"
                      href="https://10267.redirect.appmetrica.yandex.com/mainView?appmetrica_tracking_id=170895231946863928"
                      target="_blank" rel="noopener noreferrer" data-tid="7777859c"><img
                        src="//avatars.mds.yandex.net/get-bunker/50064/9de0796ad18834328b4d4858b524bf8ce6f31f98/svg"
                        alt="Загрузить приложение" loading="lazy"></a><a class="styles_store__JFbwQ"
                      href="https://redirect.appmetrica.yandex.com/serve/603240792315703184" target="_blank"
                      rel="noopener noreferrer" data-tid="7777859c"><img
                        src="//avatars.mds.yandex.net/get-bunker/994123/d4d889eb60c34ed8ca7d3c0fe965b8327e229fcf/svg"
                        alt="Загрузить приложение" loading="lazy"></a><a class="styles_store__JFbwQ"
                      href="https://redirect.appmetrica.yandex.com/serve/1179706852124993595" target="_blank"
                      rel="noopener noreferrer" data-tid="7777859c"><img
                        src="//avatars.mds.yandex.net/get-bunker/128809/1b6561563c22de1014279a528719f4f7d9360296/svg"
                        alt="Загрузить приложение" loading="lazy"></a>
                  </div>
                  <section class="styles_bottomSection__qx7AY footer__bottom">
                    <div class="styles_bottomSectionInfo__0XSte footer__bottom-info">
                      <div>
                        <span class="styles_year__tYQPp footer__bottom-info-year">©&nbsp;2003 —
                          <!-- -->2022
                          <!-- -->,
                        </span><a class="styles_bottomSectionInfoLink__Z8Szl footer__bottom-info-link"
                          href="https://www.kinopoisk.ru/" target="_blank" rel="noopener noreferrer"
                          data-tid="2e9b873">Кинопоиск</a><span class="styles_age__sKz6S footer__bottom-info-age">18
                          <!-- -->+
                        </span>
                      </div>
                      <div class="styles_infoName__8KP42">
                        Yandex Service AG
                      </div>
                    </div>
                    <ul class="styles_bottomSectionMenu__kJDDt footer__bottom-links">
                      <li class="styles_bottomSectionMenuItem__RV9c1 footer__bottom-item" data-tid="99325639"><a
                          href="https://tv.yandex.ru" target="_blank" rel="noopener noreferrer"
                          class="styles_bottomSectionMenuLink__oh5dU footer__bottom-link" data-tid="2e9b873">Телепрограмма</a>
                      </li>
                      <li class="styles_bottomSectionMenuItem__RV9c1 footer__bottom-item" data-tid="99325639"><a
                          href="https://music.yandex.ru" target="_blank" rel="noopener noreferrer"
                          class="styles_bottomSectionMenuLink__oh5dU footer__bottom-link" data-tid="2e9b873">Музыка</a></li>
                      <li class="styles_bottomSectionMenuItem__RV9c1 footer__bottom-item" data-tid="99325639"><a
                          href="https://afisha.yandex.ru" target="_blank" rel="noopener noreferrer"
                          class="styles_bottomSectionMenuLink__oh5dU footer__bottom-link" data-tid="2e9b873">Афиша</a></li>
                    </ul>
                    <div class="styles_companySection__2U1gC footer__bottom-company">
                      <span class="styles_companySectionTitle__UUuEV footer__bottom-company-name">Проект компании</span><a
                        class="styles_companyLogo__gDzdb footer__bottom-company-logo" href="https://yandex.ru" target="_blank"
                        rel="noopener noreferrer" data-tid="2e9b873">Яндекс</a>
                    </div>
                  </section>
                </footer>
              </div><button class="styles_root__p7NQg" type="button" data-tid="ed9136fe"><span
                  class="styles_iconWrapper__VsEKC"><span class="styles_icon__6tiLC"></span></span></button>
              <div class="styles_root__yEgpj styles_notifyTooltip__B_TG7" data-tid="2f7f876f"></div>
            </div>
            <div class="styles_progress__ZoYH9" hidden data-tid="c2959803">
              <div class="styles_progressBar__p3Spc"></div>
            </div>
          </div>
        </body>

        </html>
        """"
}
