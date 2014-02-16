
all: cahier_charge.pdf architecture.pdf

cahier_charge.pdf: 
	pdflatex cahier_charge.tex

architecture.pdf:
	pdflatex architecture.tex


clean:
	rm -f *.pdf *.aux *.log *.bbl *.blg *.dvi *.out

dvi: biblio.tex ref.bib
	latex biblio.tex
	bibtex biblio
	latex biblio.tex
	latex biblio.tex

pdf: dvi 
	dvipdf biblio.dvi