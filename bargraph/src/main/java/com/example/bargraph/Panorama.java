package com.example.bargraph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zph.glpanorama.GLPanorama;

/**
 * 类名称：Panorama
 * 类描述：全景图
 * 创建人：ghl
 * 创建时间：
 * 修改人：ghl
 * 修改时间：
 *
 * @version v1.0
 */
public class Panorama extends AppCompatActivity {
    private GLPanorama mLp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);
        //
        mLp = (GLPanorama) findViewById(R.id.glp);
        //传入你的全景图
        mLp.setGLPanorama(R.mipmap.mountain);
    }
}
