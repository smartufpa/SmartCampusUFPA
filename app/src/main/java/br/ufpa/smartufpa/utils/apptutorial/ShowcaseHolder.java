package br.ufpa.smartufpa.utils.apptutorial;

import com.github.amlcurran.showcaseview.targets.Target;

/**
 * Stable Commit (20/09)
 * @author kaeuchoa
 */

public class ShowcaseHolder {
    private  Target target;
    private String text;
    private int btnPosition = 0;

    public ShowcaseHolder(Target target, String text, int btnPosition) {
        this.target = target;
        this.text = text;
        this.btnPosition = btnPosition;
    }

    public ShowcaseHolder(Target target, String text) {
        this.target = target;
        this.text = text;
    }

    public Target getTarget() {
        return target;
    }

    public String getText() {
        return text;
    }

    public int getBtnPosition() {
        return btnPosition;
    }
}
