package com.margelo.nitro.HelloWorld

import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LogActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val logs = HybridHelloWorld.instance?.getLogsForNativePage()
            ?: emptyList()

        // =====================================================
        // ANA EKRAN
        // =====================================================

        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(245, 246, 248))
        }

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { view, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                        WindowInsetsCompat.Type.displayCutout()
            )

            view.setPadding(
                24,
                systemBars.top + 12,
                24,
                systemBars.bottom + 12
            )

            insets
        }

        // =====================================================
        // HEADER
        // =====================================================

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.WHITE)
            setPadding(0, 12, 0, 16)
        }

        // -------------------------
        // GERİ BUTONU
        // -------------------------

        val backButton = object : View(this) {

            private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.BLACK
                style = Paint.Style.STROKE
                strokeWidth = 5f
                strokeCap = Paint.Cap.ROUND
                strokeJoin = Paint.Join.ROUND
            }

            override fun onDraw(canvas: Canvas) {
                super.onDraw(canvas)

                canvas.drawLine(32f, 26f, 20f, 26f, paint)
                canvas.drawLine(20f, 26f, 30f, 16f, paint)
                canvas.drawLine(20f, 26f, 30f, 36f, paint)
            }
        }

        backButton.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 18f
            setColor(Color.rgb(235, 236, 240))
        }

        backButton.setOnClickListener {
            finish()
        }

        val backParams = LinearLayout.LayoutParams(
            52,
            52
        )

        backParams.setMargins(0, 0, 12, 0)

        header.addView(backButton, backParams)

        // -------------------------
        // BAŞLIK
        // -------------------------

        val titleContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val title = TextView(this).apply {
            text = "Network Logs"
            textSize = 22f
            setTextColor(Color.rgb(20, 20, 20))
        }

        val subtitle = TextView(this).apply {
            text = "Recent requests"
            textSize = 14f
            setTextColor(Color.rgb(120, 120, 120))
        }

        titleContainer.addView(title)
        titleContainer.addView(subtitle)

        val titleParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )

        header.addView(titleContainer, titleParams)

        // -------------------------
        // LOG SAYISI
        // -------------------------

        val count = TextView(this).apply {
            text = "${logs.size}/10"
            textSize = 14f
            setTextColor(Color.rgb(70, 70, 70))
            gravity = Gravity.CENTER
            setPadding(14, 0, 14, 0)

            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f
                setColor(Color.rgb(235, 236, 240))
            }
        }

        val countParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            40
        )

        header.addView(count, countParams)

        mainLayout.addView(header)

        // =====================================================
        // SCROLL ALANI
        // =====================================================

        val scrollView = ScrollView(this).apply {
            setBackgroundColor(Color.rgb(245, 246, 248))
            isFillViewport = true
        }

        val logLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 16, 0, 16)
        }

        // =====================================================
        // BOŞ LOG DURUMU
        // =====================================================

        if (logs.isEmpty()) {

            val emptyText = TextView(this).apply {
                text = "Henüz request logu yok."
                textSize = 16f
                setTextColor(Color.GRAY)
                gravity = Gravity.CENTER
                setPadding(20, 50, 20, 50)
            }

            logLayout.addView(
                emptyText,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )

        } else {

            logs.forEachIndexed { index, log ->

                // =================================================
                // STATUS CODE BUL
                // =================================================

                val statusCode = extractStatusCode(log)

                val isSuccess = statusCode != null &&
                        statusCode in 200..399

                val isFailed = statusCode != null &&
                        statusCode >= 400

                // =================================================
                // KART
                // =================================================

                val card = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(20, 18, 20, 18)

                    background = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 20f
                        setColor(Color.WHITE)

                        when {
                            isSuccess -> {
                                setStroke(
                                    3,
                                    Color.rgb(210, 240, 220)
                                )
                            }

                            isFailed -> {
                                setStroke(
                                    3,
                                    Color.rgb(250, 215, 215)
                                )
                            }

                            else -> {
                                setStroke(
                                    2,
                                    Color.rgb(235, 236, 240)
                                )
                            }
                        }
                    }

                    elevation = 2f
                }

                // =================================================
                // ÜST SATIR
                // =================================================

                val topRow = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                }

                // REQUEST NUMARASI
                val requestTitle = TextView(this).apply {
                    text = "REQUEST #${index + 1}"
                    textSize = 12f
                    setTextColor(Color.rgb(130, 130, 130))
                }

                topRow.addView(
                    requestTitle,
                    LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                )

                // =================================================
                // METHOD BADGE
                // =================================================

                val method = extractMethod(log)

                if (method != null) {

                    val methodBadge = TextView(this).apply {
                        text = method
                        textSize = 11f
                        setTextColor(Color.rgb(50, 90, 150))
                        gravity = Gravity.CENTER
                        setPadding(12, 0, 12, 0)

                        background = GradientDrawable().apply {
                            shape = GradientDrawable.RECTANGLE
                            cornerRadius = 18f
                            setColor(Color.rgb(225, 237, 255))
                        }
                    }

                    val methodParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        32
                    )

                    methodParams.setMargins(
                        8,
                        0,
                        8,
                        0
                    )

                    topRow.addView(
                        methodBadge,
                        methodParams
                    )
                }

                // =================================================
                // STATUS BADGE
                // =================================================

                if (statusCode != null) {

                    val statusBadge = TextView(this).apply {

                        text = if (isSuccess) {
                            "SUCCESS $statusCode"
                        } else {
                            "FAILED $statusCode"
                        }

                        textSize = 11f
                        gravity = Gravity.CENTER
                        setPadding(12, 0, 12, 0)

                        if (isSuccess) {

                            setTextColor(
                                Color.rgb(35, 125, 70)
                            )

                            background = GradientDrawable().apply {
                                shape = GradientDrawable.RECTANGLE
                                cornerRadius = 18f
                                setColor(
                                    Color.rgb(
                                        220,
                                        245,
                                        228
                                    )
                                )
                            }

                        } else {

                            setTextColor(
                                Color.rgb(180, 45, 45)
                            )

                            background = GradientDrawable().apply {
                                shape = GradientDrawable.RECTANGLE
                                cornerRadius = 18f
                                setColor(
                                    Color.rgb(
                                        252,
                                        225,
                                        225
                                    )
                                )
                            }
                        }
                    }

                    topRow.addView(
                        statusBadge,
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            32
                        )
                    )
                }

                card.addView(topRow)

                // =================================================
                // LOG İÇERİĞİ
                // =================================================

                val logText = TextView(this).apply {
                    text = log
                    textSize = 15f
                    setTextColor(Color.rgb(35, 35, 35))
                    setPadding(0, 14, 0, 0)
                    setLineSpacing(0f, 1.1f)
                }

                card.addView(logText)

                // =================================================
                // KARTA EKLE
                // =================================================

                val cardParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                cardParams.setMargins(
                    0,
                    0,
                    0,
                    14
                )

                logLayout.addView(
                    card,
                    cardParams
                )
            }
        }

        scrollView.addView(logLayout)

        mainLayout.addView(
            scrollView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        setContentView(mainLayout)

        ViewCompat.requestApplyInsets(mainLayout)
    }

    // =========================================================
    // STATUS CODE ÇIKAR
    // =========================================================

    private fun extractStatusCode(log: String): Int? {

        val regex = Regex(
            """RESPONSE:\s*(\d{3})"""
        )

        return regex
            .find(log)
            ?.groupValues
            ?.getOrNull(1)
            ?.toIntOrNull()
    }

    // =========================================================
    // HTTP METHOD ÇIKAR
    // =========================================================

    private fun extractMethod(log: String): String? {

        val regex = Regex(
            """REQUEST:\s+([A-Z]+)"""
        )

        return regex
            .find(log)
            ?.groupValues
            ?.getOrNull(1)
    }

    override fun onBackPressed() {
        finish()
    }
}