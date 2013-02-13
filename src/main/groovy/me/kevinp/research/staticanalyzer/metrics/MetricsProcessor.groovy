package me.kevinp.research.staticanalyzer.metrics

import gr.spinellis.ckjm.CkjmOutputHandler
import gr.spinellis.ckjm.MetricsFilter
import me.kevinp.research.staticanalyzer.model.ClassMetrics
import org.springframework.stereotype.Component

@Component
class MetricsProcessor {

    def process(classNames, closure){

        MetricsFilter.runMetrics(classNames as String[],
                {clazz, classMetrics ->
                    closure(clazz, classMetrics)
                } as CkjmOutputHandler, false)
    }
}
