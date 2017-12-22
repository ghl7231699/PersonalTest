package com.ghl.init

import org.gradle.api.Plugin
import org.gradle.api.Project

class Test implements Plugin<Project> {

    @Override
    void apply(Project project) {
        System.out.println("========================")
        System.out.println("this is my first Gradle Plugin")
        System.out.println("========================")
        project.task("",{})
    }
}